package com.davidbragadeveloper.usecases.repositories

import arrow.core.Try
import com.davidbragadeveloper.domain.ProgNetLocation

interface LocationRepository{

    suspend fun findLastLocation(): Try<ProgNetLocation?>
}