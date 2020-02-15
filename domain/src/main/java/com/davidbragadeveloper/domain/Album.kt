package com.davidbragadeveloper.domain

import java.io.Serializable

data class Album(
    val id: Long,
    val title: String,
    val year: String,
    val coverImage: String,
    val country: String,
    val tracks: List<Track> = listOf()
): Serializable
