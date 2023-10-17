package com.example.vividize_unleashyourself.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vividize_unleashyourself.data.model.FiveStepsSession
import com.example.vividize_unleashyourself.data.model.JournalEntry
import com.example.vividize_unleashyourself.data.model.MeditationSession
import com.example.vividize_unleashyourself.data.model.Quote
import com.example.vividize_unleashyourself.data.remote.QuotesApiService
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

const val TAG = "AppRepository"

class AppRepository @Inject constructor(
    private val apiService: QuotesApiService,
    private val boxStore: BoxStore,
) {

    //Boxes
    private val fiveStepsSessionBox: Box<FiveStepsSession> = boxStore.boxFor()
    private val meditationSessionBox: Box<MeditationSession> = boxStore.boxFor()
    private val journalEntriesBox: Box<JournalEntry> = boxStore.boxFor()


    //DailyQuote

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

    private val fiveStepsSubscription =
        fiveStepsSessionBox.query().build().subscribe().observer { updatedItems ->
            _fiveStepSessions.postValue(updatedItems)
        }

    fun addFiveStepSession(session: FiveStepsSession) {
        fiveStepsSessionBox.put(session)
    }

    fun removeFiveStepSession(session: FiveStepsSession) {
        fiveStepsSessionBox.remove(session)
    }


    //Medidations section

    private val _meditationSessions =
        MutableStateFlow<MutableList<MeditationSession>>(mutableListOf())

    val meditationSession = _meditationSessions.asStateFlow()

    private val meditationSubscription =
        meditationSessionBox.query().build().subscribe().observer { updatedItems ->
            _meditationSessions.value = updatedItems
        }

    fun addMeditationSession(session: MeditationSession) {
        meditationSessionBox.put(session)
    }

    fun removeMeditationSession(session: MeditationSession) {
        meditationSessionBox.remove(session)
    }

    //Journaling Section
    private val _journalEntries =
        MutableStateFlow<MutableList<JournalEntry>>(mutableListOf())

    val journalEntries = _journalEntries.asStateFlow()

    private val journalSubscription =
       journalEntriesBox.query().build().subscribe().observer { updatedItems ->
            _journalEntries.value = updatedItems
        }


    fun addJournalEntry(entry: JournalEntry) {
        journalEntriesBox.put(entry)

    }

    fun removeJournalEntry(entry: JournalEntry) {
         journalEntriesBox.remove(entry)
    }



    //Cleanup to prevent Memory Leaks
    fun closeSubscriptions() {
        fiveStepsSubscription.cancel()
        meditationSubscription.cancel()
        journalSubscription.cancel()
    }
}