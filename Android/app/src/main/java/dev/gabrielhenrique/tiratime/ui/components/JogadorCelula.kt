package dev.gabrielhenrique.tiratime.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gabrielhenrique.tiratime.R
import dev.gabrielhenrique.tiratime.data.Jogador
import dev.gabrielhenrique.tiratime.ui.theme.ConvocadosDaSelecaoTheme

@Composable
fun JogadorCelula(jogador: Jogador, onRemove: (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .background(
                MaterialTheme.colors.secondary,
                RoundedCornerShape(16.dp)
            )
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val iconId =
            if (jogador.ehGoleiro) R.drawable.baseline_sports_handball_24 else R.drawable.baseline_sports_soccer_24
        Icon(
            painter = painterResource(id = iconId),
            tint = MaterialTheme.colors.contentColorFor(MaterialTheme.colors.secondary),
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = jogador.nome,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.contentColorFor(MaterialTheme.colors.secondary)
        )
        onRemove?.let {
            Spacer(modifier = Modifier.weight(1.0f))
            IconButton(modifier = Modifier.size(18.dp), onClick = {
                it()
            }) {
                Icon(
                    tint = MaterialTheme.colors.contentColorFor(MaterialTheme.colors.secondary),
                    painter = painterResource(id = R.drawable.baseline_delete_24),
                    contentDescription = "",
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun JogadorCelulaPreview() {
    val jogador = Jogador(
        nome = "Gabriel",
        ehGoleiro = true
    )

    ConvocadosDaSelecaoTheme {
        repeat(4) {
            JogadorCelula(jogador = jogador, onRemove = {})
        }
    }
}