package com.example.vividize_unleashyourself.data.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class JournalEntry(
    val id: Int,
    var topic: String,
    var content: String,
    val timestamp: LocalDateTime = LocalDateTime.now()
) {
    val formattedTimestamp: String = formatTimestamp(timestamp)


    private fun formatTimestamp(time: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
        return time.format(formatter)
    }
}
