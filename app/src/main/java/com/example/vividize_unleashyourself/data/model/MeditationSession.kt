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
    var setLenght: Long? = null
) {


    lateinit var meditation: ToOne<Meditation>

    var guided: Boolean = true
        get() = meditation.target?.guided ?: false

    var duration: Long = 0
        get() = if (guided) meditation.target?.duration ?: 0 else setLenght?: 0



    val formattedDateTimestamp: String = "$datestamp - $timestamp"
    var intention: String = ""
    var note: String = ""
    var moodStart: Double = 0.0
    var moodEnd: Double = 0.0



}