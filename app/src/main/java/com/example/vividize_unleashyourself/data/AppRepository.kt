package com.example.vividize_unleashyourself.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vividize_unleashyourself.data.model.Quote
import com.example.vividize_unleashyourself.data.remote.QuotesApi

const val TAG = "AppRepository"

class AppRepository(private val api: QuotesApi) {

    private val _dailyQuote = MutableLiveData<Quote>()

    val dailyQuote: LiveData<Quote>
        get() = _dailyQuote

    suspend fun getQuote(id: String) {
        try {
            _dailyQuote.postValue(
                api.retrofitService.getQuote(
                    id
                )
            )
        } catch (e: Exception) {
            Log.d(TAG, "API Call failed $e")
        }
    }
}