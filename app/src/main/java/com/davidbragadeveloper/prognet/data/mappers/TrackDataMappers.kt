package com.davidbragadeveloper.prognet.data.mappers

import com.davidbragadeveloper.domain.Track
import com.davidbragadeveloper.prognet.data.remote.DiscogsTrack

fun DiscogsTrack.toDomain() = Track(
    title = this.title,
    duration = this.duration,
    position = this.position
)