package dev.gabrielhenrique.tiratime

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.gabrielhenrique.tiratime.data.Jogador
import dev.gabrielhenrique.tiratime.navigation.Telas
import dev.gabrielhenrique.tiratime.ui.components.Adicionador
import dev.gabrielhenrique.tiratime.ui.components.JogadorCelula
import dev.gabrielhenrique.tiratime.ui.theme.ConvocadosDaSelecaoTheme
import kotlinx.coroutines.launch

@Composable
fun JogadoresTela(
    navController: NavController,
    viewModel: MainViewModel,
    scaffoldState: ScaffoldState
) {
    val clipboardManager = LocalClipboardManager.current
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Button(onClick = {
                viewModel.sortearTimes()
                navController.navigate(Telas.Times.route) {
                    launchSingleTop = true
                }
            }) {
                Text(text = stringResource(id = R.string.button_create_team))
            }

            Button(onClick = {
                viewModel.deletarTudo()
                coroutineScope.launch {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        "Deletou todos os jogadores",
                        "Desfazer"
                    )

                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.desfazerDelete()
                    }
                }
            }) {
                Text("Deletar tudo!")
            }

            Button(onClick = {
                val text = viewModel.times.joinToString(separator = "\n") {
                    val jogadores = it.jogadores.joinToString(", ")
                    "Time $jogadores"
                }
                clipboardManager.setText(AnnotatedString(text))
            }) {
                Text("Copiar para o clipboard!")
            }

            if (viewModel.jogadores.any { it.ehGoleiro }) {
                Text("Goleiros")
            }
            viewModel.jogadores.filter { it.ehGoleiro }.forEach {
                Spacer(Modifier.height(8.dp))
                JogadorCelula(it, onRemove = {
                    viewModel.removerJogador(it.nome)
                })
            }

            if (viewModel.jogadores.any { !it.ehGoleiro }) {
                Text("Jogadores de linha")
            }
            viewModel.jogadores.filter { !it.ehGoleiro }.forEach {
                Spacer(Modifier.height(8.dp))
                JogadorCelula(it, onRemove = {
                    viewModel.removerJogador(it.nome)
                })
            }
            Text("Quantidade de jogadores: ${viewModel.jogadores.size}")
            Adicionador(
                modifier = Modifier
                    .padding(top = 16.dp),
                nomeJogador = viewModel.nomeJogador,
                onNomeEdited = { viewModel.nomeJogador = it },
                onAdd = { nome, ehGoleiro ->
                    viewModel.adicionarJogador(
                        Jogador(
                            nome = nome,
                            ehGoleiro = ehGoleiro
                        )
                    )
                }
            )
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun JogadoresTelaPreview() {
    val viewModel: MainViewModel = viewModel()
    viewModel.adicionarJogador(Jogador("Gabriel", true))
    viewModel.adicionarJogador(Jogador("Gabriel", false))
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    ConvocadosDaSelecaoTheme {
        JogadoresTela(
            navController,
            viewModel,
            scaffoldState
        )
    }
}