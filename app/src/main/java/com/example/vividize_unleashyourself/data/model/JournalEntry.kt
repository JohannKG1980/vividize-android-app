package com.example.vividize_unleashyourself.data.model

import com.example.vividize_unleashyourself.utils.getCurrentDate
import com.example.vividize_unleashyourself.utils.getCurrentTime
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
data class JournalEntry(
    @Id var id: Long = 0,
    val datestamp: String = getCurrentDate(),
    val timestamp: String = getCurrentTime()
) {
    var content: String = ""
    val formattedDateTimestamp: String = "$datestamp - $timestamp"
}
