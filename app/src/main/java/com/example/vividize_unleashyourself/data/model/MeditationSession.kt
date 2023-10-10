package com.example.vividize_unleashyourself.data.model

import com.example.vividize_unleashyourself.utils.getCurrentDate
import com.example.vividize_unleashyourself.utils.getCurrentTime
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne


@Entity
data class MeditationSession(

    @Id var sessionId: Long = 0,
    val datestamp: String = getCurrentDate(),
    val timestamp: String = getCurrentTime(),
    val setLenght: Long? = null
) {


    lateinit var meditation: ToOne<Meditation>

    val guided: Boolean
        get() = meditation.target?.guided ?: false

    val duration: Long
        get() = if (guided) meditation.target?.duration ?: 0 else setLenght!!



    val formattedDateTimestamp: String = "$datestamp - $timestamp"
    var intention: String = ""
    var note: String = ""
    var moodStart: Int = 0
    var moodEnd: Int = 0



}