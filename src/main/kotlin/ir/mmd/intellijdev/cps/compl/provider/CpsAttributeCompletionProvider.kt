package ir.mmd.intellijdev.cps.compl.provider

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.util.ProcessingContext
import ir.mmd.intellijdev.cps.compl.lookup.CpsAttributeLookupElement

class CpsAttributeCompletionProvider : CompletionProvider<CompletionParameters>() {
	override fun addCompletions(
		parameters: CompletionParameters,
		context: ProcessingContext,
		result: CompletionResultSet
	) = result.addAllElements(
		CpsAttributeLookupElement.of(
			"name",
			"run-at",
		)
	)
}
