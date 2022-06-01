package ir.mmd.intellijdev.cps.compl

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.patterns.XmlPatterns.*
import ir.mmd.intellijdev.cps.compl.provider.CpsAttributeCompletionProvider
import ir.mmd.intellijdev.cps.compl.provider.CpsAttributeValueCompletionProvider
import ir.mmd.intellijdev.cps.compl.provider.CpsTagNameCompletionProvider

class CpsXmlCompletionContributor : CompletionContributor() {
	init {
		extend(
			CompletionType.BASIC,
			psiElement().inside(xmlTag()).afterLeaf("<"),
			CpsTagNameCompletionProvider()
		)
		
		extend(
			CompletionType.BASIC,
			psiElement().inside(xmlAttribute()).andNot(
				psiElement().inside(xmlAttributeValue())
			),
			CpsAttributeCompletionProvider()
		)
		
		extend(
			CompletionType.BASIC,
			psiElement().inside(xmlAttributeValue()),
			CpsAttributeValueCompletionProvider()
		)
	}
}
