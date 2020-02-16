package com.davidbragadeveloper.domain

import java.io.Serializable

data class Track(
    val duration : String,
    val position: String,
    val title: String
):Serializable