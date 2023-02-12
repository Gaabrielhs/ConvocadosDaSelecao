package dev.gabrielhenrique.tiratime

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import dev.gabrielhenrique.tiratime.data.Jogador
import dev.gabrielhenrique.tiratime.data.Time
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.ceil

val regex = Regex("""^[0-9]+[ ]*[-]+[ ]+(.*)$""", setOf(RegexOption.MULTILINE))

class MainViewModel : ViewModel() {

    val jogadoresPorTime = 5

    var jogadores by mutableStateOf(listOf<Jogador>())

    val mostrarCopiarDoClipboard = MutableStateFlow(false)

    var times by mutableStateOf(listOf<Time>())

    var nomeJogador by mutableStateOf("")

    fun adicionarJogador(jogador: Jogador) {
        if (jogador.nome.isBlank()) return
        jogadores = jogadores.toMutableList().apply { add(jogador) }
    }

    fun removerJogador(name: String) {
        val listaDeJogadores = jogadores.toMutableList()
        listaDeJogadores.removeIf { it.nome == name }
        jogadores = listaDeJogadores
    }

    fun sortearTimes() {
        val jogadoresParaSorteio = jogadores.filter { !it.ehGoleiro }.toMutableList()
        val goleirosDisponiveisParaSorteio = jogadores.filter { it.ehGoleiro }.toMutableList()
        val totalTimes = ceil(jogadoresParaSorteio.size / jogadoresPorTime.toFloat()).toInt()
        val times = (0 until totalTimes).map {
            val quantidadeMaximaDeJogadores =
                jogadoresParaSorteio.size.coerceAtMost(jogadoresPorTime)
            val jogadores = (0 until quantidadeMaximaDeJogadores).map {
                val jogadorSorteado = jogadoresParaSorteio.random()
                jogadoresParaSorteio.remove(jogadorSorteado)
                jogadorSorteado
            }.toMutableList()

            //TODO Adicionar goleiro
            val goleiro = goleirosDisponiveisParaSorteio.randomOrNull()
            goleiro?.let {
                goleirosDisponiveisParaSorteio.remove(it)
                jogadores.add(it)
            }

            Time(
                nome = if (jogadores.size >= jogadoresPorTime) "Time ${it + 1}" else "Reservas",
                jogadores = jogadores,
            )
        }

        this.times = times
    }

    private var jogadoresDeletados: List<Jogador> = listOf()
    private var timesDeletados: List<Time> = listOf()

    var mostrarMensagemDelete by mutableStateOf(false)

    fun deletarTudo() {
        jogadoresDeletados = jogadores
        timesDeletados = times

        jogadores = listOf()
        times = listOf()

        mostrarMensagemDelete = true
    }

    fun desfazerDelete() {
        jogadores = jogadoresDeletados
        times = timesDeletados
    }

    fun carregarListaDoClipboard(clipboard: AnnotatedString?) {
        val textoCopiado = clipboard.toString()
        val textoDivididoPorGoleirosEJogadores = textoCopiado.split("Jogadores")
        val goleiros = textoDivididoPorGoleirosEJogadores.first()
        val jogadores = textoDivididoPorGoleirosEJogadores.last()

        val nomesGoleiros = regex.findAll(goleiros).map { it.groupValues[1] }
        val nomesJogadores = regex.findAll(jogadores).map { it.groupValues[1] }

        nomesGoleiros.forEach {
            adicionarJogador(
                Jogador(nome = it, ehGoleiro = true)
            )
        }

        nomesJogadores.forEach {
            adicionarJogador(
                Jogador(nome = it)
            )
        }
    }

    fun validarClipboard(text: AnnotatedString?): Boolean {
        val clipboard = text.toString()
        return regex.containsMatchIn(clipboard)
    }
}