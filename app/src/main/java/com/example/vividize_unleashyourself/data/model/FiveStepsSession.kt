package com.example.vividize_unleashyourself.data.model

import com.example.vividize_unleashyourself.feature_vms.CurrentStep
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class FiveStepsSession(
    val sessionId: Long = 0,
    val stepCycles: MutableList<FiveSteps> = mutableListOf(),
    val datestamp: LocalDate = LocalDate.now(),
    val timestamp: LocalTime = LocalTime.now()
) {
    val formattedDateTimestamp: String = formatDatestamp(datestamp) + "-" + formatTimestamp(timestamp)

    private fun formatDatestamp(time: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy ")
        return time.format(formatter)
    }

    private fun formatTimestamp(time: LocalTime): String {
        val formatter = DateTimeFormatter.ofPattern(" HH:mm:ss")
        return time.format(formatter)
    }
}