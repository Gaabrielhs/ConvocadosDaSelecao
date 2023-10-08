package dev.gabrielhenrique.tiratime

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.gabrielhenrique.tiratime.data.Jogador
import dev.gabrielhenrique.tiratime.navigation.Navigation
import dev.gabrielhenrique.tiratime.navigation.Telas
import dev.gabrielhenrique.tiratime.ui.JogadoresTela
import dev.gabrielhenrique.tiratime.ui.TimesTela
import dev.gabrielhenrique.tiratime.ui.components.Adicionador
import dev.gabrielhenrique.tiratime.ui.components.JogadorCelula
import dev.gabrielhenrique.tiratime.ui.theme.ConvocadosDaSelecaoTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private var diceMediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerPlaySound()
        setContent { MainActivityContent() }

        //TODO: Update window toolbar to adapt the background (battery, cellular toolbar)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        viewModel.showSnackbar(hasFocus)
    }

    override fun onStart() {
        super.onStart()
        diceMediaPlayer = MediaPlayer.create(this, R.raw.rolling_dice)
    }


    override fun onStop() {
        super.onStop()
        diceMediaPlayer?.release()
        diceMediaPlayer = null
    }

    private fun registerPlaySound() {
        // Play a sound when the flow is collected
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.playDiceSound.collect {
                    println("playing sound")
                    try {
                        // If already is playing
                        diceMediaPlayer?.stop()
                        diceMediaPlayer?.prepare()
                        diceMediaPlayer?.start()
                    } catch (e: Throwable) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun MainActivityContent() {
        ConvocadosDaSelecaoTheme {
            val navController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            Scaffold(
                snackbarHost = { SnackbarHost(snackbarHostState) },
                bottomBar = {
                    MainBottomBar(currentDestination, navController)
                }
            ) { innerPadding ->
                val clipboardManager = LocalClipboardManager.current
                LaunchedEffect(key1 = true) {
                    viewModel.mostrarCopiarDoClipboard.collect {
                        Log.d("Clipboard", "collect")
                        val clipboardText = clipboardManager.getText()
                        if (viewModel.validarClipboard(clipboardText)) {
                            val result = snackbarHostState.showSnackbar(
                                "Copiar lista de jogadores do clipboard?",
                                "Copiar!"
                            )

                            if (result == SnackbarResult.ActionPerformed) {
                                viewModel.carregarListaDoClipboard(clipboardText)
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
                            snackbarHostState = snackbarHostState
                        )
                    }

                    composable(Telas.Times.route) {
                        TimesTela(navController = navController, viewModel = viewModel)
                    }
                }
            }
        }
    }

    @Composable
    private fun MainBottomBar(
        currentDestination: NavDestination?,
        navController: NavHostController
    ) {
        Box {
            NavigationBar {
                Navigation.telas.forEach { tela ->
                    NavigationBarItem(
                        icon = { Icon(tela.icon, contentDescription = null) },
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
                                //  restoreState = true
                            }
                        },
                    )
                }
            }
            if (currentDestination?.hierarchy?.any { it.route == Telas.Times.route } == true) {
                SortearButton(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = (-32).dp),
                    onSortear = viewModel::sortearTimes
                )
            }
        }
    }
}

@Composable
fun SortearButton(modifier: Modifier = Modifier, onSortear: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val sizeScale by animateFloatAsState(if (isPressed) 1.2f else 1f, label = "size scale")
    var rotationDegree by remember { mutableStateOf(0f) }
    val rotation = animateFloatAsState(
        animationSpec = tween(
            durationMillis = 1000
        ),
        targetValue = rotationDegree,
        label = "rotation",
    )

    FloatingActionButton(
        modifier = modifier.graphicsLayer {
            scaleX = sizeScale
            scaleY = sizeScale
        },
        onClick = {
            onSortear()
            rotationDegree = if (rotationDegree >= 360f) 0f else 360f
        },
        containerColor = MaterialTheme.colorScheme.primary,
        interactionSource = interactionSource
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .rotate(rotation.value),
            tint = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.primary),
            imageVector = Icons.Default.Casino,
            contentDescription = null
        )
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
            JogadorCelula(jogador = Jogador(nome = "Gabriel", true), onRemove = {})
        }
    }
}