package dev.gabrielhenrique.tiratime.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gabrielhenrique.tiratime.data.Jogador
import dev.gabrielhenrique.tiratime.ui.theme.ConvocadosDaSelecaoTheme

@Composable
fun JogadorCelula(modifier: Modifier = Modifier, jogador: Jogador, onRemove: (() -> Unit)? = null) {
    Row(
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.secondary,
                RoundedCornerShape(16.dp)
            )
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.getPlayerIcon(jogador.ehGoleiro),
            tint = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.secondary),
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = jogador.nome,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.secondary)
        )
        onRemove?.let {
            Spacer(modifier = Modifier.weight(1.0f))
            IconButton(modifier = Modifier.size(18.dp), onClick = it) {
                Icon(
                    imageVector = Icons.Default.PersonRemove,
                    tint = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.secondary),
                    contentDescription = ""
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