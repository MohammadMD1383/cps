package ir.mmd.intellijdev.cps.compl

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.xml.XmlTokenType
import com.intellij.util.ProcessingContext

class CpsXmlCompletionContributor : CompletionContributor() {
	init {
		extend(
			CompletionType.BASIC,
			PlatformPatterns.psiElement(XmlTokenType.XML_START_TAG_START),
			object : CompletionProvider<CompletionParameters>() {
				override fun addCompletions(
					parameters: CompletionParameters,
					context: ProcessingContext,
					result: CompletionResultSet
				) {
					result.addElement(object : LookupElement() {
						override fun getLookupString(): String {
							return "starter"
						}
					})
				}
			}
		)
	}
}