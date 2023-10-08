package dev.gabrielhenrique.tiratime.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.gabrielhenrique.tiratime.R
import dev.gabrielhenrique.tiratime.ui.theme.ConvocadosDaSelecaoTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Adicionador(
    modifier: Modifier = Modifier,
    nomeJogador: String,
    onNomeEdited: (String) -> Unit,
    onAdd: (String, Boolean) -> Unit
) {
    var goleiroSelected by remember { mutableStateOf(false) }
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            shape = RoundedCornerShape(16.dp),
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
                    imageVector = Icons.Default.getPlayerIcon(goleiroSelected),
                    contentDescription = null
                )
            },
            value = nomeJogador,
            modifier = Modifier
                .weight(1.0f, true),
            singleLine = true,
            maxLines = 1,
            onValueChange = onNomeEdited,
            keyboardActions = KeyboardActions(onDone = {
                onAdd(nomeJogador, goleiroSelected)
                onNomeEdited("")
            }),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
            ),
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            ),
            onClick = {
                onAdd(nomeJogador, goleiroSelected)
                onNomeEdited("")
            }) {
            Icon(
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.primary),
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }

    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun AdicionadorPreview() {
    ConvocadosDaSelecaoTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            Adicionador(nomeJogador = "Gabriel", onNomeEdited = {}, onAdd = { _, _ -> })
        }
    }
}