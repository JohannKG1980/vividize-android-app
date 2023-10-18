package com.example.vividize_unleashyourself.feature_vms

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import com.example.vividize_unleashyourself.data.AppRepository
import com.example.vividize_unleashyourself.data.model.JournalEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import hilt_aggregated_deps._com_example_vividize_unleashyourself_Vividize_GeneratedInjector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

enum class undoRedoAction { UNDO, REDO, NODO }

@HiltViewModel
class JournalingViewModel @Inject constructor(
    private val repository: AppRepository,
    application: Application,
) : AndroidViewModel(application) {

    val allEntries = repository.journalEntries

    private val _editAction = MutableStateFlow<undoRedoAction>(undoRedoAction.NODO)
    val editAction = _editAction.asStateFlow()

    private val _currentEntry = MutableStateFlow<JournalEntry?>(null)

    val currentEntry = _currentEntry.asStateFlow()

    private val _currentContent = MutableStateFlow<String>("")
    val currentContent = _currentContent.asStateFlow()

    fun triggerUndo() {
        _editAction.value = undoRedoAction.UNDO
    }

    fun triggerRedo() {
        _editAction.value = undoRedoAction.REDO
    }
    fun resetActionTrigger() {
        _editAction.value = undoRedoAction.NODO
    }

    fun newEntry() {

        _currentEntry.value = JournalEntry()


    }

    fun contentBuffer(input: String) {
        _currentContent.value = input

    }

    fun saveEntry() {

        if (_currentEntry.value != null) {
            _currentEntry.value!!.content = _currentContent.value
            repository.addJournalEntry(_currentEntry.value!!)
            _currentEntry.value = null
            _currentContent.value = ""

        }

    }


}
