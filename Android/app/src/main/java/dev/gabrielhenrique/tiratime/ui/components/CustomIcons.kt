package dev.gabrielhenrique.tiratime.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsMma
import androidx.compose.material.icons.filled.SportsSoccer

fun Icons.Filled.getPlayerIcon(ehGoleiro: Boolean) =
    if (ehGoleiro) Icons.Filled.SportsMma else Icons.Filled.SportsSoccer