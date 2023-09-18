package com.example.vividize_unleashyourself.data.model

data class FiveStepsSession(
    val sessionId: Long,
    val stepCycles: MutableList<FiveSteps> = mutableListOf()
)