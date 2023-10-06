package com.example.vividize_unleashyourself.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vividize_unleashyourself.data.model.FiveStepsSession
import com.example.vividize_unleashyourself.data.model.Quote
import com.example.vividize_unleashyourself.data.remote.QuotesApi
import com.example.vividize_unleashyourself.data.remote.QuotesApiService
import javax.inject.Inject

const val TAG = "AppRepository"

class AppRepository @Inject constructor(private val apiService: QuotesApiService) {

    private val _dailyQuote = MutableLiveData<Quote>()

    val dailyQuote: LiveData<Quote>
        get() = _dailyQuote

    suspend fun getQuote(id: String) {
        try {
            _dailyQuote.postValue(
                apiService.getQuote().random()
            )
        } catch (e: Exception) {
            Log.d(TAG, "API Call failed $e")
        }
    }


    //Five Step Method Section
    private val _fiveStepSessions = MutableLiveData<MutableList<FiveStepsSession>>(mutableListOf())
    val fiveStepsSessions: LiveData<MutableList<FiveStepsSession>>
        get() = _fiveStepSessions

    fun addFiveStepSession(session: FiveStepsSession) {
        _fiveStepSessions.value?.add(session)
        _fiveStepSessions.value = _fiveStepSessions.value
    }
    fun removeFiveStepSession(session: FiveStepsSession) {
        _fiveStepSessions.value?.remove(session)
        _fiveStepSessions.value = _fiveStepSessions.value
    }

}