package ir.mmd.intellijdev.cps.compl.lookup

import com.intellij.codeInsight.completion.InsertionContext
import com.intellij.codeInsight.lookup.LookupElement

class CpsAttributeLookupElement(private val lookupValue: String) : LookupElement() {
	companion object {
		inline fun of(vararg lookupValues: String) = lookupValues.map { CpsAttributeLookupElement(it) }
	}
	
	override fun getLookupString() = lookupValue
	
	override fun handleInsert(context: InsertionContext) {
		val editor = context.editor
		val document = context.document
		val caret = editor.caretModel.primaryCaret
		val offset = caret.offset
		
		document.insertString(offset, "=\"\"")
		caret.moveToOffset(offset + 2)
	}
}
