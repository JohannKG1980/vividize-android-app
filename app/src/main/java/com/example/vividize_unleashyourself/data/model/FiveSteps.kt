package com.example.vividize_unleashyourself.data.model

import com.example.vividize_unleashyourself.R

data class FiveSteps(val cycleId: Int) {
    val stepOne = Question(R.string.stepOne)
    val stepOneRepeat = Question(R.string.stepOneRepeat)
    val stepOneInput = ""


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
    var stepThreeFollowAnswer = false


    val stepFour = Question(R.string.stepFour)
    var stepFourAnswer = ""


    val stepFive = Question(R.string.stepFive)
    val intensity = 0


    val repeatQuestion = Question(R.string.askForRepeat)
    var repeatAnswer = false

}
