package com.example.vividize_unleashyourself.data.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class AppSettings(@Id var id: Long = 0) {

    //Five Steps Settings
    var instructionWatched: Boolean = false
    var audioContentActive: Boolean = true


    //Meditation Settings
    var meditationAudioActive: Boolean = true


}
