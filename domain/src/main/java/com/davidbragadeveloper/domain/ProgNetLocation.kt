package com.davidbragadeveloper.domain

import java.io.Serializable


data class ProgNetLocation(
    val latitude: CoordinateMeasure,
    val longitude: CoordinateMeasure
):Serializable