package com.example.vividize_unleashyourself.feature_vms

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import com.example.vividize_unleashyourself.data.AppRepository
import com.example.vividize_unleashyourself.data.model.Meditation
import com.example.vividize_unleashyourself.data.model.MeditationSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


enum class currentState { SELECTING_SESSION, GUIDED_INIT, UNGUIDED_INIT, SESSION_RUNNING, SESSION_END, NO_SESSION }

@HiltViewModel
class MeditationsViewModel @Inject constructor(
    private val repository: AppRepository,
    private val mediaPlayer: MediaPlayer,
    application: Application,
) : AndroidViewModel(application) {


    val meditations = listOf(
        Meditation(1, "Priming", true, "Morning mental priming Session", duration = 15000L),
        Meditation(2, "I am the Field", true, duration = 30000L),
        Meditation(3, "Open Session", false)

    )

    val allSessions = repository.meditationSession
    private val _currentSession = MutableStateFlow<MeditationSession?>(null)
    val currentSession = _currentSession.asStateFlow()


    private val _currentViewState = MutableStateFlow(currentState.NO_SESSION)
    val currentViewState = _currentViewState.asStateFlow()


    fun cancelSession() {
        _currentViewState.value = currentState.NO_SESSION
        _currentSession.value = null
    }

    fun openSessionSelector() {
        _currentViewState.value = currentState.SELECTING_SESSION
    }

    fun openSession(meditation: Meditation) {
        _currentSession.value = MeditationSession()
        if (_currentSession.value != null) {
            _currentSession.value!!.meditation.target = meditation
        }
        if(meditation.guided) {
            _currentViewState.value = currentState.GUIDED_INIT
        } else {
            _currentViewState.value = currentState.UNGUIDED_INIT
        }
    }





}