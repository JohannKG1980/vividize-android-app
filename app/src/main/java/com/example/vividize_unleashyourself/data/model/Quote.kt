package com.example.vividize_unleashyourself.data.model

import com.example.vividize_unleashyourself.utils.getCurrentDate
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Quote(
    @Id(assignable = true) var id: Long = 0,
    val Author: String = "",
    val Quote_en: String = "",
    val quote_de: String = "",
    val bg_img_url: String = "",
    val aut_img_url: String = "",
) {

    val datestamp: String = getCurrentDate()

}