package ir.mmd.intellijdev.cps.builder

import com.intellij.icons.AllIcons
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.module.ModuleTypeManager
import javax.swing.Icon

class CustomModuleType : ModuleType<CustomModuleBuilder>(ID) {
	companion object {
		const val ID = "ir.mmd.intellijdev.cps.builder.CustomModuleType"
		val instance get() = ModuleTypeManager.getInstance().findByID(ID) as CustomModuleType
	}
	
	override fun createModuleBuilder() = CustomModuleBuilder()
	override fun getName() = "Custom Project Starter"
	override fun getDescription() = "Custom project starter"
	override fun getNodeIcon(isOpened: Boolean): Icon = AllIcons.Nodes.Module
}