package dev.gabrielhenrique.tiratime.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.gabrielhenrique.tiratime.R

@Composable
fun PosicaoDropDown(
    dropDownState: Boolean,
    goleiroSelecionado: Boolean,
    onDropDawnStateChanged: (Boolean) -> Unit,
    onGoleiroSelecionadoChanged: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .clickable {
                onDropDawnStateChanged(true)
            }
            .focusable(false),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(if (goleiroSelecionado) R.drawable.baseline_sports_handball_24 else R.drawable.baseline_sports_soccer_24),
            contentDescription = null
        )
        DropdownMenu(
            expanded = dropDownState,
            onDismissRequest = {
                onGoleiroSelecionadoChanged(false)
                onDropDawnStateChanged(false)
            }
        ) {
            DropdownMenuItem(onClick = {
                onGoleiroSelecionadoChanged(true)
                onDropDawnStateChanged(false)
            }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(R.drawable.baseline_sports_handball_24),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Goleiro")
            }
            DropdownMenuItem(onClick = {
                onGoleiroSelecionadoChanged(false)
                onDropDawnStateChanged(false)
            }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(R.drawable.baseline_sports_soccer_24),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Linha")
            }
        }
    }
}