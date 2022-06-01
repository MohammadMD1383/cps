package ir.mmd.intellijdev.cps.builder.wizard

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.openapi.options.ConfigurationException
import ir.mmd.intellijdev.cps.xml.Starter
import java.io.File

class CustomModuleWizardStep : ModuleWizardStep() {
	private val panel by lazy { ModuleWizardUI(getStarterTemplates()) }
	lateinit var starter: Starter private set
	
	override fun validate() = if (panel.starter != null) true else throw ConfigurationException("Please select a starter template")
	override fun getComponent() = panel.component
	
	override fun updateDataModel() {
		starter = panel.starter!!
	}
	
	private fun getStarterTemplates(): Map<String, Starter> {
		val starter = XmlMapper().readValue(
			File("/Files/projects/plugin/intellij/Custom Project Starter/src/main/resources/schema/sample.xml"),
			Starter::class.java
		)
		return mapOf("Default" to starter)
	}
}
