package com.example.vividize_unleashyourself.data.model

import com.example.vividize_unleashyourself.R
import com.example.vividize_unleashyourself.utils.getCurrentDate
import com.example.vividize_unleashyourself.utils.getCurrentTime
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne


@Entity
data class FiveSteps(@Id var cycleId: Long = 0) {

    lateinit var parentSession: ToOne<FiveStepsSession>


    var stepOneInput = ""
    var intensity = 0

    var stepTwoAnswer = false

    var stepThreeAnswer = false

    var stepThreeBetterBeFree = false

    var stepFourAnswer = ""

    var intensityLeft = 0

    var repeatAnswer = false

    var cycleFinished = false

    val datestamp: String = getCurrentDate()
    val timestamp: String = getCurrentTime()
    val formattedDateTimestamp: String = "$datestamp - $timestamp"


}
