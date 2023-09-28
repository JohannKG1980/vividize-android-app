package com.example.vividize_unleashyourself.data.model

import com.example.vividize_unleashyourself.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class FiveSteps(val cycleId: Int = 0) {

    val stepOne = Question(R.string.stepOne)
    val stepOneRepeat = Question(R.string.stepOneRepeat)
    var stepOneInput = ""
    var intensity = 0


    val stepTwoInstruct = Question(R.string.stepTwoGeneral)
    val stepTwo = listOf(
        Question(R.string.stepTwo1),
        Question(R.string.stepTwo2),
        Question(R.string.stepTwo3)
    )
    val stepTwoQuestion = stepTwo.random()
    var stepTwoAnswer = false


    val stepThree = listOf(
        Question(R.string.stepThree1),
        Question(R.string.stepThree2)
    )
    val stepThreeQuestion = stepThree.random()
    var stepThreeAnswer = false

    val stepThreeFollowUp = Question(R.string.stepThreeNo)
    var stepThreeBetterBeFree = false


    val stepFourQuestion = Question(R.string.stepFour)
    var stepFourAnswer = ""


    val stepFive = Question(R.string.stepFive)
    var intensityLeft = 0


    val repeatQuestion = Question(R.string.askForRepeat)
    var repeatAnswer = false

    var cycleFinished = false

    val timestamp: LocalDateTime = LocalDateTime.now()

    val formattedTimestamp: String = formatTimestamp(timestamp)

    private fun formatTimestamp(time: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
        return time.format(formatter)
    }

}
