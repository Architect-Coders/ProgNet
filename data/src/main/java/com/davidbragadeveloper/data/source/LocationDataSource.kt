package com.davidbragadeveloper.data.source

import arrow.core.Try
import com.davidbragadeveloper.domain.ProgNetLocation

interface LocationDataSource{
    suspend fun findLastLocation(): Try<ProgNetLocation?>

}