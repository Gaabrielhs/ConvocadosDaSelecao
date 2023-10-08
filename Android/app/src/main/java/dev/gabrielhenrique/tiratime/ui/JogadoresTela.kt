package dev.gabrielhenrique.tiratime.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GroupRemove
import androidx.compose.material3.Badge
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.gabrielhenrique.tiratime.MainViewModel
import dev.gabrielhenrique.tiratime.data.Jogador
import dev.gabrielhenrique.tiratime.ui.components.Adicionador
import dev.gabrielhenrique.tiratime.ui.components.JogadorCelula
import dev.gabrielhenrique.tiratime.ui.theme.ConvocadosDaSelecaoTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun JogadoresTela(
    navController: NavController,
    viewModel: MainViewModel,
    snackbarHostState: SnackbarHostState
) {
    val coroutineScope = rememberCoroutineScope()
    fun deletarTudo() {
        viewModel.deletarTudo()
        coroutineScope.launch {
            val result = snackbarHostState.showSnackbar(
                "Deletou todos os jogadores",
                "Desfazer"
            )

            if (result == SnackbarResult.ActionPerformed) {
                viewModel.desfazerDelete()
            }
        }
    }
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val goleiros by remember { derivedStateOf { viewModel.jogadores.filter { it.ehGoleiro } } }
        val jogadores by remember { derivedStateOf { viewModel.jogadores.filter { !it.ehGoleiro } } }
        val hasAnyGoleiro by remember { derivedStateOf { goleiros.isNotEmpty() } }
        val hasAnyJogador by remember { derivedStateOf { viewModel.jogadores.isNotEmpty() } }
        val listState = rememberLazyListState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 32.dp, bottom = 8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = "Jogadores",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                if (hasAnyJogador) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Badge(
                        containerColor = MaterialTheme.colorScheme.onSurface,
                    ) {
                        Text(
                            text = viewModel.jogadores.size.toString(),
                            color = MaterialTheme.colorScheme.surface
                        )
                    }
                    Spacer(modifier = Modifier.weight(1.0f))
                    ElevatedAssistChip(
                        onClick = { deletarTudo() },
                        label = {
                            Text(
                                text = "Deletar tudo"
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.GroupRemove,
                                contentDescription = null
                            )
                        },
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1.0f),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.Bottom),
                contentPadding = PaddingValues(top = 32.dp, bottom = 16.dp)
            ) {
                listaJogadores(jogadores = goleiros, onRemove = {
                    viewModel.removerJogador(it)
                })

                if (hasAnyJogador && hasAnyGoleiro) {
                    item {
                        Divider(thickness = 2.dp)
                    }
                }
                listaJogadores(jogadores = jogadores, onRemove = {
                    viewModel.removerJogador(it)
                })
            }
            Adicionador(
                modifier = Modifier.fillMaxWidth(),
                nomeJogador = viewModel.nomeJogador,
                onNomeEdited = { viewModel.nomeJogador = it },
                onAdd = { nome, ehGoleiro ->
                    viewModel.adicionarJogador(
                        Jogador(
                            nome = nome,
                            ehGoleiro = ehGoleiro
                        )
                    )
                    coroutineScope.launch {
                        listState.animateScrollToItem(viewModel.jogadores.size)
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.listaJogadores(jogadores: List<Jogador>, onRemove: (String) -> Unit) {
    items(items = jogadores, key = { it.nome }) {
        JogadorCelula(modifier = Modifier.animateItemPlacement(), jogador = it, onRemove = {
            onRemove(it.nome)
        })
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
    val snackbarHostState = remember { SnackbarHostState() }
    ConvocadosDaSelecaoTheme {
        JogadoresTela(
            navController,
            viewModel,
            snackbarHostState
        )
    }
}