package dev.gabrielhenrique.tiratime

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gabrielhenrique.tiratime.data.Jogador
import dev.gabrielhenrique.tiratime.data.Time
import dev.gabrielhenrique.tiratime.ui.components.JogadorCelula
import dev.gabrielhenrique.tiratime.ui.theme.ConvocadosDaSelecaoTheme

@Composable
fun TimeCard(modifier: Modifier = Modifier, time: Time) {
    Card(
        modifier = modifier,
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.background(MaterialTheme.colors.primary)
            ) {
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    text = time.nome,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.contentColorFor(MaterialTheme.colors.primary)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            time.jogadores.sortedByDescending { it.ehGoleiro }.forEach {
                Row(
                    modifier = Modifier.padding(4.dp)
                ) {
                    JogadorCelula(jogador = it)
                }
            }
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TimeCardPreview() {
    ConvocadosDaSelecaoTheme {
        TimeCard(
            modifier = Modifier,
            time = Time(
                nome = "Time 1",
                jogadores = listOf(
                    Jogador(nome = "Gabriel"),
                    Jogador(nome = "Gabriel", true),
                    Jogador(nome = "Gabriel"),
                    Jogador(nome = "Gabriel"),
                )
            )
        )
    }
}