package dev.gabrielhenrique.tiratime

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.gabrielhenrique.tiratime.data.Jogador
import dev.gabrielhenrique.tiratime.data.Time
import dev.gabrielhenrique.tiratime.ui.theme.ConvocadosDaSelecaoTheme

@Composable
fun TimesTela(navController: NavController, viewModel: MainViewModel) {
    if (viewModel.times.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.times_vazio_mensagem),
                style = MaterialTheme.typography.h5
            )
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp)
        ) {
            items(viewModel.times) { time ->
                TimeCard(modifier = Modifier, time = time)
            }
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TimesTelaPreview() {
    val viewModel: MainViewModel = viewModel()
    val navController = rememberNavController()

    viewModel.times = listOf(
        Time("Time 1", listOf(
            Jogador("Courtois", true),
            Jogador("Messi"),
            Jogador("Neymar"),
            Jogador("Van djik"),
            Jogador("Marquinhos"),
            Jogador("Thiago Silva"),
        )),
        Time("Time 2", listOf(
            Jogador("Lloris", true),
            Jogador("Mbappé"),
            Jogador("Haaland"),
            Jogador("Modric"),
            Jogador("Eder Militão"),
            Jogador("Lúcio"),
        )),
    )
    ConvocadosDaSelecaoTheme {
        TimesTela(navController = navController, viewModel = viewModel)
    }
}