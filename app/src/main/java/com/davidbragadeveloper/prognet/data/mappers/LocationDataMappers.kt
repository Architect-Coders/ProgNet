package com.davidbragadeveloper.prognet.data.mappers

import android.location.Location
import com.davidbragadeveloper.domain.ProgNetLocation

fun Location.toDomain(): ProgNetLocation = ProgNetLocation(
    latitude = latitude,
    longitude = longitude
)