package com.example.vividize_unleashyourself.data.model

import com.example.vividize_unleashyourself.utils.formatedDuration
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.time.Duration

@Entity
data class Meditation(
    @Id(assignable = true) var id: Long = 0,
    val name: String = "",
    var guided: Boolean = false,
    var title: String = "",
    var videoPath: String = "",
    var audioPath: String = "",
    var duration: Long = 0L,
) {
    var sessionLenght = formatedDuration(duration)
}