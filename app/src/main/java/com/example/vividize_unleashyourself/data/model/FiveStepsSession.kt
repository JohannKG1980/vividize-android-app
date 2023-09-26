package com.example.vividize_unleashyourself.data.model

import com.example.vividize_unleashyourself.feature_vms.CurrentStep
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class FiveStepsSession(
    val sessionId: Long = 0,
    val stepCycles: MutableList<FiveSteps> = mutableListOf(),
    val timestamp: LocalDateTime = LocalDateTime.now()
) {
    val formattedTimestamp: String = formatTimestamp(timestamp)

    private fun formatTimestamp(time: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
        return time.format(formatter)
    }
}