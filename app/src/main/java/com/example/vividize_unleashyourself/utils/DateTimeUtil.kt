package com.example.vividize_unleashyourself.utils

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


fun getCurrentDate(): String {
    return LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
}

fun getCurrentTime(): String {
    return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
}

fun formatedDuration(durationMillis: Long): String {
    val totalSeconds = durationMillis / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}
