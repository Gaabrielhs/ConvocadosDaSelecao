package dev.gabrielhenrique.tiratime

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dev.gabrielhenrique.tiratime.data.Jogador
import dev.gabrielhenrique.tiratime.data.Time
import dev.gabrielhenrique.tiratime.domain.CarregarListaClipboardUseCase
import dev.gabrielhenrique.tiratime.domain.SortearTimesUseCase
import dev.gabrielhenrique.tiratime.domain.ValidarClipboardUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


const val REGEX_PATTERN_COLUMN_CHILD = "regexPattern"

class MainViewModel : ViewModel() {
    private val remoteConfig = Firebase.remoteConfig

    private val sortearTimesUseCase by lazy { SortearTimesUseCase() }
    private val validarClipboardUseCase by lazy { ValidarClipboardUseCase() }
    private val carregarListaClipboardUseCase by lazy { CarregarListaClipboardUseCase() }

    private var regexPattern: String? = null
    private var jogadoresDeletados: List<Jogador> = listOf()
    private var timesDeletados: List<Time> = listOf()

    val jogadores = mutableStateListOf<Jogador>()
    val times = mutableStateListOf<Time>()
    var nomeJogador by mutableStateOf("")

    private val _mostrarCopiarDoClipboard = MutableSharedFlow<Unit>()
    val mostrarCopiarDoClipboard = _mostrarCopiarDoClipboard.asSharedFlow()

    private val _playDiceSound = MutableSharedFlow<Unit>()
    val playDiceSound = _playDiceSound.asSharedFlow()

    init {
        regexPattern = remoteConfig.getString(REGEX_PATTERN_COLUMN_CHILD)
    }

    fun adicionarJogador(jogador: Jogador) {
        if (jogador.nome.isBlank()) return
        //Prevent for add duplicated
        if(jogadores.any { it.nome == jogador.nome }) return
        jogadores.add(jogador)
    }

    fun removerJogador(name: String) {
        jogadores.removeIf { it.nome == name }
    }

    fun sortearTimes() {
        val timesSorteados = sortearTimesUseCase(jogadores = jogadores)
        println("time anterior: $times")
        println("novo time: $timesSorteados")
        times.clear()
        times.addAll(timesSorteados)

        viewModelScope.launch {
            _playDiceSound.emit(Unit)
        }
    }

    fun deletarTudo() {
        jogadoresDeletados = jogadores.toList()
        timesDeletados = times.toList()

        jogadores.clear()
        times.clear()
    }

    fun desfazerDelete() {
        jogadores.addAll(jogadoresDeletados)
        times.addAll(timesDeletados)
    }

    fun carregarListaDoClipboard(clipboard: AnnotatedString?) {
        carregarListaClipboardUseCase(regexPattern = regexPattern, clipboard = clipboard)?.let {
            jogadores.clear()
            jogadores.addAll(it)
        }
    }

    fun validarClipboard(text: AnnotatedString?): Boolean {
        return validarClipboardUseCase(regexPattern = regexPattern, clipboard = text)
    }

    fun showSnackbar(hasFocus: Boolean) {
        if (!hasFocus) {
            return
        }
        println("showSnackbar | $regexPattern")
        regexPattern?.let {
            viewModelScope.launch { _mostrarCopiarDoClipboard.emit(Unit) }
        }
    }
}