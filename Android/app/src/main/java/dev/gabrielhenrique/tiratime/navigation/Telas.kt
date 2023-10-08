package dev.gabrielhenrique.tiratime.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import dev.gabrielhenrique.tiratime.R

sealed class Telas(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Jogadores : Telas("jogadores", R.string.jogadores_tela, Icons.Filled.Person)
    object Times : Telas("times", R.string.times_tela, Icons.Filled.Groups)
}

object Navigation {
    val telas = listOf(
        Telas.Jogadores,
        Telas.Times
    )
}