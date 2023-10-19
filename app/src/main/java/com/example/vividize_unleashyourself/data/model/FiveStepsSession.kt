package com.example.vividize_unleashyourself.data.model

import com.example.vividize_unleashyourself.feature_vms.CurrentStep
import com.example.vividize_unleashyourself.utils.getCurrentDate
import com.example.vividize_unleashyourself.utils.getCurrentTime
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Entity
data class FiveStepsSession(
    @Id var sessionId: Long = 0,

    ) {
    @Backlink(to = "parentSession")
    lateinit var stepCycles: ToMany<FiveSteps>
    val datestamp: String = getCurrentDate()
    val timestamp: String = getCurrentTime()
    var sessionFinished: Boolean = false
    val formattedDateTimestamp: String = "$datestamp - $timestamp"

}