package ir.mmd.intellijdev.cps.builder

import com.intellij.grazie.utils.dropPrefix
import com.intellij.ide.util.projectWizard.ModuleBuilder
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.Disposable
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.roots.ModifiableRootModel
import ir.mmd.intellijdev.cps.builder.model.Path
import ir.mmd.intellijdev.cps.builder.model.PathType
import ir.mmd.intellijdev.cps.builder.wizard.CustomModuleWizardStep
import ir.mmd.intellijdev.cps.xml.Directory
import ir.mmd.intellijdev.cps.xml.Structure
import java.io.File
import java.nio.file.Paths

class CustomModuleBuilder : ModuleBuilder() {
	private val moduleWizardStep by lazy { CustomModuleWizardStep() }
	
	private fun Directory.resolvePaths(parentPath: String? = null): List<Path> {
		val paths = mutableListOf<Path>()
		val thisPath = this@resolvePaths.name
		val parent = parentPath?.let { "$it/" } ?: ""
		
		directories?.forEach {
			paths.add(Path("$parent$thisPath/${it.name}", PathType.Directory))
			paths.addAll(it.resolvePaths("$parent$thisPath"))
		}
		
		files?.forEach {
			paths.add(Path("$parent$thisPath/${it.name}", PathType.File))
		}
		
		return paths
	}
	
	private fun Structure.resolvePaths(): List<Path> {
		val paths = mutableListOf<Path>()
		
		directories?.forEach {
			paths.add(Path(it.name, PathType.Directory))
			paths.addAll(it.resolvePaths())
		}
		
		files?.forEach {
			paths.add(Path(it.name, PathType.File))
		}
		
		return paths
	}
	
	override fun setupRootModel(rootModel: ModifiableRootModel) {
		val starter = moduleWizardStep.starter
		val paths = starter.structure.resolvePaths()
		
		println("-------------raw paths----------------")
		paths.forEach { println(it.path) }
		
		println("-----------reduced paths--------------")
		val reducedPaths = paths.filterIndexed { i, path ->
			for (j in i + 1 until paths.size)
				if (paths[j].path.startsWith(path.path)) return@filterIndexed false
			return@filterIndexed true
		}.onEach { println(it.path) }
		
		val basePath = doAddContentEntry(rootModel)!!.url.dropPrefix("file:")
		
		println("-------------final paths--------------")
		val finalPaths = reducedPaths.map {
			Path(Paths.get(basePath, it.path).toString(), it.type)
		}.onEach(::println)
		
		finalPaths.forEach {
			File(it.path).apply {
				if (it.type == PathType.File) {
					parentFile.mkdirs()
					createNewFile()
				} else mkdirs()
				
				
			}
		}
	}
	
	override fun getCustomOptionsStep(context: WizardContext?, parentDisposable: Disposable?) = moduleWizardStep
	override fun getModuleType(): ModuleType<*> = CustomModuleType.instance
}
