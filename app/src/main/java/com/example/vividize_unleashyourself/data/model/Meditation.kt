package com.example.vividize_unleashyourself.data.model

import java.time.Duration

data class Meditation (
    val id: Long,
    val name: String,
    val title: String,
    val videoPath: String,
    val audioPath: String,
    val duration: Long
)