package dev.gabrielhenrique.tiratime.domain

import dev.gabrielhenrique.tiratime.data.Jogador
import dev.gabrielhenrique.tiratime.data.Time
import kotlin.math.ceil

// TODO: Create unit tests for it
class SortearTimesUseCase {

    val jogadoresPorTime = 5
    operator fun invoke(jogadores: List<Jogador>): List<Time> {
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
        return times
    }
}