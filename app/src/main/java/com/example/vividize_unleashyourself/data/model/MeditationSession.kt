package com.example.vividize_unleashyourself.data.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class MeditationSession(
    val sessionId: Long,
    val sessionType: Meditation,
    val timestamp: LocalDateTime = LocalDateTime.now()
) {
    val formattedTimestamp: String = formatTimestamp(timestamp)
    var note: String = ""
    var moodStart: Int = 0
    var moodEnd: Int = 0
    var duration: Long = 0
    var meditationId : Long = 0



    private fun formatTimestamp(time: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
        return time.format(formatter)
    }
}