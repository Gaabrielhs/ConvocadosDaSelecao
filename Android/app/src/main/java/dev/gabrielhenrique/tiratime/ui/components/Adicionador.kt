package dev.gabrielhenrique.tiratime.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gabrielhenrique.tiratime.R
import dev.gabrielhenrique.tiratime.ui.theme.ConvocadosDaSelecaoTheme


@Composable
fun Adicionador(
    modifier: Modifier = Modifier,
    nomeJogador: String,
    onNomeEdited: (String) -> Unit,
    onAdd: (String, Boolean) -> Unit
) {
    var goleiroSelected by remember { mutableStateOf(false) }
    Column {
        Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                label = {
                    Text("Nome")
                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .clickable {
                                goleiroSelected = !goleiroSelected
                            },
                        painter = painterResource(if (goleiroSelected) R.drawable.baseline_sports_handball_24 else R.drawable.baseline_sports_soccer_24),
                        contentDescription = null
                    )
                },
                value = nomeJogador,
                modifier = Modifier
                    .weight(1.0f, true),
                singleLine = true,
                maxLines = 1,
                onValueChange = onNomeEdited,
                keyboardActions = KeyboardActions {
                    onAdd(nomeJogador, goleiroSelected)
                    onNomeEdited("")
                },
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(modifier = Modifier
                .size(48.dp)
                .background(
                    color = MaterialTheme.colors.primary,
                    shape = CircleShape
                ),
                onClick = {
                    onAdd(nomeJogador, goleiroSelected)
                    onNomeEdited("")
                }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colors.contentColorFor(MaterialTheme.colors.primary),
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun AdicionadorPreview() {
    ConvocadosDaSelecaoTheme {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            Adicionador(nomeJogador = "Gabriel", onNomeEdited = {}, onAdd = { _, _ -> })
        }
    }
}