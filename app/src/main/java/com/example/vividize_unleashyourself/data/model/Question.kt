package com.example.vividize_unleashyourself.data.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id


@Entity
data class Question(
    val content: Int = 0,
    val audioPath: String = "",
    @Id var id: Long = 0)