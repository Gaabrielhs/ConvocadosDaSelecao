package dev.gabrielhenrique.tiratime.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gabrielhenrique.tiratime.data.Jogador
import dev.gabrielhenrique.tiratime.data.Time
import dev.gabrielhenrique.tiratime.ui.theme.ConvocadosDaSelecaoTheme

@Composable
fun TimeCard(modifier: Modifier = Modifier, time: Time) {
    val jogadores = remember(time) { time.jogadores.sortedByDescending { it.ehGoleiro } }
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(),
        shape = CardDefaults.elevatedShape
    ) {
        Column(
            modifier = Modifier.padding(bottom = 4.dp),
        ) {
            Row(
                modifier = Modifier.background(MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    text = time.nome,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.primary)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            jogadores.forEach {
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