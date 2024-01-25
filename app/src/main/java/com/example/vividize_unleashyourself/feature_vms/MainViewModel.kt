package com.example.vividize_unleashyourself.feature_vms

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.vividize_unleashyourself.data.AppRepository
import com.example.vividize_unleashyourself.data.remote.QuotesApi
import com.example.vividize_unleashyourself.utils.getCurrentDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class ApiStatus { LOADING, ERROR, DONE }

const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AppRepository,
    application: Application,
) : AndroidViewModel(application) {



    var meditationSessions = repository.meditationSessions

    var fiveStepSessions = repository.fiveStepsSessions


    val todaysQuote = repository.dailyQuote

    private val _loading = MutableLiveData<ApiStatus>()
    val loading: LiveData<ApiStatus>
        get() = _loading

    private val _mentalStartTab = MutableLiveData<Int?>(null)
    val mentalStartTab: LiveData<Int?>
        get() = _mentalStartTab


    init {

        loadQuote()

    }

    private fun loadQuote() {


        viewModelScope.launch {
            _loading.value = ApiStatus.LOADING
            try {
                repository.getQuote()

                _loading.value = ApiStatus.DONE

            } catch (e: Exception) {
                Log.e(TAG, "ERROR LOADING    DATA : $e")
                _loading.value = ApiStatus.ERROR
            }
        }


    }

    fun controlQuickstart(input: Int?) {
        _mentalStartTab.value = input
        _mentalStartTab.value = _mentalStartTab.value

    }

    fun cleanMemory() {
        repository.closeSubscriptions()
    }

}