package ir.mmd.intellijdev.cps.builder

import com.intellij.ide.util.projectWizard.ModuleBuilder
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.Disposable
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.roots.ModifiableRootModel
import ir.mmd.intellijdev.cps.builder.wizard.CustomModuleWizardStep
import ir.mmd.intellijdev.cps.util.invoke
import ir.mmd.intellijdev.cps.xml.*
import java.io.File as JFile

class CustomModuleBuilder : ModuleBuilder() {
	private val moduleWizardStep by lazy { CustomModuleWizardStep() }
	
	private fun Structure.traverse(consumer: (Any) -> Unit) {
		val path = contentEntryPath!!
		
		directories?.forEach {
			it.traverse(path, consumer)
		}
		
		files?.forEach {
			consumer(it.withParentPath(path))
		}
	}
	
	private fun Directory.traverse(parentPath: String, consumer: (Any) -> Unit) {
		consumer(withParentPath("$parentPath/"))
		val path = "$parentPath/$name"
		
		directories?.forEach {
			consumer(it.withParentPath(path))
			it.traverse(path, consumer)
		}
		
		files?.forEach {
			consumer(it.withParentPath(path))
		}
	}
	
	override fun setupRootModel(rootModel: ModifiableRootModel) {
		doAddContentEntry(rootModel)
		
		ProgressManager.getInstance().run(object : Task.Backgroundable(rootModel.project, "Initializing custom project...", false) {
			override fun run(indicator: ProgressIndicator) {
				indicator.isIndeterminate = true
				
				val starter = moduleWizardStep.starter
				val endScripts = mutableListOf<Script>()
				val directory = JFile(contentEntryPath!!)
				
				indicator.text = "Executing beginning scrips..."
				starter.scripts?.forEach {
					when (it.runAt ?: RunAt.Beginning) {
						RunAt.Beginning -> it.content.let { content ->
							indicator.text2 = content
							content.invoke(directory).waitFor()
						}
						
						RunAt.End -> endScripts += it
					}
				}
				indicator.text2 = ""
				
				indicator.text = "Creating project structure..."
				starter.structure?.traverse {
					when (it) {
						is Directory -> JFile(it.name).mkdirs()
						is File -> JFile(it.name).apply {
							createNewFile()
							it.content?.apply { writeBytes(trimIndent().encodeToByteArray()) }
						}
					}
				}
				
				indicator.text = "Executing end scripts..."
				endScripts.forEach {
					it.content.let { content ->
						indicator.text2 = content
						content.invoke(directory).waitFor()
					}
				}
			}
		})
	}
	
	override fun getCustomOptionsStep(context: WizardContext?, parentDisposable: Disposable?) = moduleWizardStep
	override fun getModuleType(): ModuleType<*> = CustomModuleType.instance
}
