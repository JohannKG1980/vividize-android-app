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
    var feeling: Int = 0


    private fun formatTimestamp(time: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
        return time.format(formatter)
    }
}