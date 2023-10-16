package com.example.vividize_unleashyourself.feature_vms

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import com.example.vividize_unleashyourself.data.AppRepository
import com.example.vividize_unleashyourself.data.model.JournalEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class JournalingViewModel @Inject constructor(
    private val repository: AppRepository,
    application: Application,
) : AndroidViewModel(application) {

    val allEntries = repository.journalEntries

    private val _currentEntry = MutableStateFlow<JournalEntry?>(null)

    val currentEntry = _currentEntry.asStateFlow()

    fun newEntry() {
        if(_currentEntry.value == null) {
            _currentEntry.value = JournalEntry()
        }

    }

}
