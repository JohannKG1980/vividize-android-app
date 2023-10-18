package com.example.vividize_unleashyourself.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vividize_unleashyourself.data.model.FiveStepsSession
import com.example.vividize_unleashyourself.data.model.JournalEntry
import com.example.vividize_unleashyourself.data.model.MeditationSession
import com.example.vividize_unleashyourself.data.model.Quote
import com.example.vividize_unleashyourself.data.model.Quote_
import com.example.vividize_unleashyourself.data.remote.QuotesApiService
import com.example.vividize_unleashyourself.utils.getCurrentDate
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.Property
import io.objectbox.kotlin.boxFor
import io.objectbox.query.QueryBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

const val TAG = "AppRepository"

class AppRepository @Inject constructor(
    private val apiService: QuotesApiService,
   boxStore: BoxStore,
) {

    //Boxes
    private val fiveStepsSessionBox: Box<FiveStepsSession> = boxStore.boxFor()
    private val meditationSessionBox: Box<MeditationSession> = boxStore.boxFor()
    private val journalEntriesBox: Box<JournalEntry> = boxStore.boxFor()
    private val dailyQuoteBox: Box<Quote> = boxStore.boxFor()


    //DailyQuote

    private val _dailyQuote = MutableLiveData<Quote>()

    val dailyQuote: LiveData<Quote>
        get() = _dailyQuote

    private val dailyQuoteSubscription =
        dailyQuoteBox.query().build().subscribe().observer { updatedItem ->
            _dailyQuote.postValue(updatedItem[0])
        }

    private fun clearQuoteBox() {
        dailyQuoteBox.removeAll()
    }


    suspend fun getQuote() {
       val storedQuote = dailyQuoteBox.all.firstOrNull()

        if (_dailyQuote.value == null || storedQuote?.datestamp != getCurrentDate()) {
            try {
                val newQuote = apiService.getQuote().random()
                clearQuoteBox()
                dailyQuoteBox.put(newQuote)
            } catch (e: Exception) {
                Log.d(TAG, "API Call failed $e")
            }

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
        dailyQuoteSubscription.cancel()
    }


}
