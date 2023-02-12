package dev.gabrielhenrique.tiratime.navigation

import androidx.annotation.StringRes
import dev.gabrielhenrique.tiratime.R

sealed class Telas(val route: String, @StringRes val resourceId: Int) {
    object Jogadores : Telas("jogadores", R.string.jogadores_tela)
    object Times : Telas("times", R.string.times_tela)
}

object Navigation {
    val telas = listOf(
        Telas.Jogadores,
        Telas.Times
    )
}