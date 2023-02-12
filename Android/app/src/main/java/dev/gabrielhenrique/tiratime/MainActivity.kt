package dev.gabrielhenrique.tiratime

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.gabrielhenrique.tiratime.data.Jogador
import dev.gabrielhenrique.tiratime.navigation.Navigation
import dev.gabrielhenrique.tiratime.navigation.Telas
import dev.gabrielhenrique.tiratime.ui.components.Adicionador
import dev.gabrielhenrique.tiratime.ui.components.JogadorCelula
import dev.gabrielhenrique.tiratime.ui.theme.ConvocadosDaSelecaoTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConvocadosDaSelecaoTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                val clipboardManager = LocalClipboardManager.current

                Scaffold(
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        BottomNavigation {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            Navigation.telas.forEach { tela ->
                                BottomNavigationItem(
                                    icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                                    label = { Text(stringResource(tela.resourceId)) },
                                    selected = currentDestination?.hierarchy?.any { it.route == tela.route } == true,
                                    onClick = {
                                        navController.navigate(tela.route) {
                                            // Pop up to the start destination of the graph to
                                            // avoid building up a large stack of destinations
                                            // on the back stack as users select items
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            // Avoid multiple copies of the same destination when
                                            // reselecting the same item
                                            launchSingleTop = true
                                            // Restore state when reselecting a previously selected item
//                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    LaunchedEffect(key1 = true) {
                        viewModel.mostrarCopiarDoClipboard.collect {
                            Log.d("Clipboard", "collect")
                            if (it) {
                                val clipboardText = clipboardManager.getText()
                                if (viewModel.validarClipboard(clipboardText)) {
                                    val result = scaffoldState.snackbarHostState.showSnackbar(
                                        "Copiar lista de jogadores do clipboard?",
                                        "Copiar!"
                                    )

                                    if (result == SnackbarResult.ActionPerformed) {
                                        viewModel.carregarListaDoClipboard(clipboardText)
                                    }
                                }
                            }
                        }
                    }

                    NavHost(
                        navController,
                        startDestination = Telas.Jogadores.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Telas.Jogadores.route) {
                            JogadoresTela(
                                navController = navController,
                                viewModel = viewModel,
                                scaffoldState = scaffoldState
                            )
                        }

                        composable(Telas.Times.route) {
                            TimesTela(navController = navController, viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("Clipboard", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Clipboard", "onResume")
        viewModel.mostrarCopiarDoClipboard.value = true
    }

    override fun onPause() {
        super.onPause()
        Log.d("Clipboard", "onPause")
        viewModel.mostrarCopiarDoClipboard.value = false
    }

    override fun onStop() {
        super.onStop()
        Log.d("Clipboard", "onStop")
    }

    override fun onDestroy() {
        Log.d("Clipboard", "onDestroy")
        super.onDestroy()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ConvocadosDaSelecaoTheme {
        Column {
            var nomeJogador by remember { mutableStateOf("") }
            Adicionador(
                nomeJogador = nomeJogador,
                onNomeEdited = { nomeJogador = it }) { nome, posicao -> }
            Spacer(modifier = Modifier.height(9.dp))
            JogadorCelula(Jogador(nome = "Gabriel", true), {})
        }
    }
}