package com.example.vividize_unleashyourself.feature_vms

import android.app.Application
import android.media.MediaPlayer
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import com.example.vividize_unleashyourself.R
import com.example.vividize_unleashyourself.data.AppRepository
import com.example.vividize_unleashyourself.data.model.Meditation
import com.example.vividize_unleashyourself.data.model.MeditationSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.Duration
import javax.inject.Inject


enum class currentState { SELECTING_SESSION, GUIDED_INIT, UNGUIDED_INIT, SESSION_RUNNING, SESSION_END, NO_SESSION }

@HiltViewModel
class MeditationsViewModel @Inject constructor(
    private val repository: AppRepository,
    private val mediaPlayer: MediaPlayer,
    application: Application,
) : AndroidViewModel(application) {


    val meditations = listOf(
        Meditation(1, "Priming", true, "Morning mental priming Session", duration = 900000L, cardBg= R.drawable.priming_item),
        Meditation(2, "I am the Field", true, duration = 1800000L, cardBg = R.drawable.guided_session),
        Meditation(3, "Open Session", false, cardBg = R.drawable.free_field_session)
    )

    val allSessions = repository.meditationSession
    private val _currentSession = MutableStateFlow<MeditationSession?>(null)
    val currentSession = _currentSession.asStateFlow()


    private val _currentViewState = MutableStateFlow(currentState.NO_SESSION)
    val currentViewState = _currentViewState.asStateFlow()

    private var timer: CountDownTimer? = null
    private val _remainingTime = MutableStateFlow(0L)
    val remainingTime = _remainingTime.asStateFlow()

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

    fun initSession(customDuration: Long = 0, initMood: Double, intention: String) {
        _currentSession.value?.let { session ->
            if (!session.guided) {
                session.setLenght = customDuration
            }
            session.intention = intention
            session.moodStart = initMood

            _currentViewState.value = currentState.SESSION_RUNNING
        }
    }

    fun startTimer(duration: Long) {
        timer = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _remainingTime.value = millisUntilFinished
            }

            override fun onFinish() {
                // Handle timer finish logic
            }
        }.start()

//        // Start the MediaPlayer
//        mediaPlayer.start()
    }

    fun pauseTimer() {
        timer?.cancel()
//        mediaPlayer.pause()
    }

    fun resetTimer() {
        timer?.cancel()
        _remainingTime.value = 0
//        mediaPlayer.reset()
    }

    fun setMediaPlayerDataSource(source: String) {
        mediaPlayer.setDataSource(source)
        mediaPlayer.prepare()
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
//        mediaPlayer.release()
    }



}