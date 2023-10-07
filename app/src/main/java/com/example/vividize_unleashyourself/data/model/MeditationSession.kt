package com.example.vividize_unleashyourself.data.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class MeditationSession(
    val sessionId: Long,
    val guided: Boolean,
    val datestamp: LocalDate = LocalDate.now(),
    val timestamp: LocalTime = LocalTime.now(),
    val meditation: Meditation,
    val setLenght: Long?
) {

    val formattedDateTimestamp: String = formatDatestamp(datestamp) + "-" + formatTimestamp(timestamp)
    var intention: String = ""
    var note: String = ""
    var moodStart: Int = 0
    var moodEnd: Int = 0
    var duration: Long = if(guided) {meditation.duration} else { setLenght!! }

    val guidedMeditations = listOf<Meditation>()


    private fun formatDatestamp(time: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy ")
        return time.format(formatter)
    }

    private fun formatTimestamp(time: LocalTime): String {
        val formatter = DateTimeFormatter.ofPattern(" HH:mm:ss")
        return time.format(formatter)
    }
}