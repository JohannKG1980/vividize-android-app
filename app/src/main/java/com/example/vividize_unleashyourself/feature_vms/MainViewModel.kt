package com.example.vividize_unleashyourself.feature_vms

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.vividize_unleashyourself.data.AppRepository
import com.example.vividize_unleashyourself.data.remote.QuotesApi
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE }

const val TAG = "MainViewModel"


class MainViewModel(application: Application) : AndroidViewModel(application) {

    val repository = AppRepository(QuotesApi)

    val todaysQuote = repository.dailyQuote

    private val _loading = MutableLiveData<ApiStatus>()
    val loading: LiveData<ApiStatus>
        get() = _loading

    private val _mentalStartTab = MutableLiveData<Int?>(null)
    val mentalStartTab: LiveData<Int?>
        get() = _mentalStartTab

    private val _quickStart = MutableLiveData<Boolean>(false)
    val quickStart: LiveData<Boolean>
        get() = _quickStart


    init {
        loadQuote()
    }

    private fun loadQuote() {

        val id = (1..44).random().toString()
        viewModelScope.launch {
            _loading.value = ApiStatus.LOADING
            try {
                repository.getQuote(id)

                _loading.value = ApiStatus.DONE

            } catch (e: Exception) {
                Log.e(TAG, "ERROR LOADING    DATA : $e")
                _loading.value = ApiStatus.ERROR
            }
        }

    }
    fun controlQuickstart(input: Int?, isQuickStart: Boolean) {
        _quickStart.value = isQuickStart
        _quickStart.value = _quickStart.value
        _mentalStartTab.value = input
        _mentalStartTab.value = _mentalStartTab.value

    }

}