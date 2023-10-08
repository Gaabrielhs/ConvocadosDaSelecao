package dev.gabrielhenrique.tiratime.domain

import androidx.compose.ui.text.AnnotatedString
import dev.gabrielhenrique.tiratime.data.Jogador

class CarregarListaClipboardUseCase {

    private val jogadoresDivider = "Jogadores"

    // TODO: Create unit tests for it
    operator fun invoke(regexPattern: String?, clipboard: AnnotatedString?): List<Jogador>? {
        if (regexPattern.isNullOrBlank() || clipboard?.text.isNullOrBlank()) return null

        val regex = Regex(regexPattern, setOf(RegexOption.MULTILINE))
        val textoCopiado = clipboard.toString()
        val textoDivididoPorGoleirosEJogadores = textoCopiado.split(jogadoresDivider)
        val goleiros = textoDivididoPorGoleirosEJogadores.first()
        val jogadores = textoDivididoPorGoleirosEJogadores.last()

        val nomesGoleiros = regex.findAll(goleiros).map { it.groupValues[1] }.toList()
        val nomesJogadores = regex.findAll(jogadores).map { it.groupValues[1] }.toList()

        if (nomesGoleiros.size + nomesJogadores.size == 0) return null

        return nomesGoleiros.map {
            Jogador(nome = it, ehGoleiro = true)
        } + nomesJogadores.map {
            Jogador(nome = it)
        }
    }
}