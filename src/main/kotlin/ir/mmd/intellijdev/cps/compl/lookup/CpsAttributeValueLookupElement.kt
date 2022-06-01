package ir.mmd.intellijdev.cps.compl.lookup

import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementPresentation
import com.intellij.icons.AllIcons

class CpsAttributeValueLookupElement(private val lookupValue: String) : LookupElement() {
	companion object {
		inline fun of(vararg lookupValues: String) = lookupValues.map { CpsAttributeValueLookupElement(it) }
	}
	
	override fun getLookupString() = lookupValue
	override fun renderElement(presentation: LookupElementPresentation) {
		super.renderElement(presentation)
		presentation.icon = AllIcons.Nodes.Enum
	}
}
