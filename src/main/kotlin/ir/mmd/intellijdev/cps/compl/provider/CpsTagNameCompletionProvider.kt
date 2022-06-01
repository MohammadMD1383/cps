package ir.mmd.intellijdev.cps.compl.provider

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.util.ProcessingContext
import ir.mmd.intellijdev.cps.compl.lookup.CpsTagLookupElement

class CpsTagNameCompletionProvider : CompletionProvider<CompletionParameters>() {
	override fun addCompletions(
		parameters: CompletionParameters,
		context: ProcessingContext,
		result: CompletionResultSet
	) = result.addAllElements(
		CpsTagLookupElement.of(
			"starter",
			"structure",
			"directory",
			"file",
			"scripts",
			"script"
		)
	)
}
