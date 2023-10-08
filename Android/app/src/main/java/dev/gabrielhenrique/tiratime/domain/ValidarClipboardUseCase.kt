package dev.gabrielhenrique.tiratime.domain

import androidx.compose.ui.text.AnnotatedString

// TODO: Create unit tests for it
class ValidarClipboardUseCase {
    operator fun invoke(regexPattern: String?, clipboard: AnnotatedString?): Boolean {
        if (regexPattern.isNullOrBlank()) return false
        val regex = Regex(regexPattern, setOf(RegexOption.MULTILINE))
        return regex.containsMatchIn(clipboard.toString())
    }
}