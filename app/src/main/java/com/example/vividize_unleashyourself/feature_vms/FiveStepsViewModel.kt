package com.example.vividize_unleashyourself.feature_vms

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vividize_unleashyourself.data.AppRepository
import com.example.vividize_unleashyourself.data.model.FiveStepsSession
import com.example.vividize_unleashyourself.data.remote.QuotesApi

class FiveStepsViewModel(application: Application) : AndroidViewModel(application) {


    private val repository = AppRepository(QuotesApi)
    val allSessions = repository.fiveStepsSessions

    private val _currentSession = MutableLiveData<FiveStepsSession>()
    val currentSession: LiveData<FiveStepsSession>
        get() = _currentSession


    fun addSession() {
        if (currentSession.value != null)
            repository.addFiveStepSession(currentSession.value!!)
    }

    fun removeSession() {
        if (currentSession.value != null)
            repository.removeFiveStepSession(currentSession.value!!)
    }
}