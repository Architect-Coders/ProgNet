package com.davidbragadeveloper.usecases.usecases

import arrow.core.Try
import arrow.syntax.function.curried
import com.davidbragadeveloper.domain.Album
import com.davidbragadeveloper.usecases.repositories.AlbumsRepository
import com.davidbragadeveloper.usecases.repositories.LocationRepository

typealias ToggleAlbumHeared = suspend (Album) -> Try<Boolean>




fun buildToggleAlbumHearedUseCase(
    albumsRepository: AlbumsRepository,
    locationRepository: LocationRepository
): ToggleAlbumHeared = { album ->
    with(album){
        locationRepository.findLastLocation().fold(
            ifFailure = {
                copy(heared = !heared)
            },
            ifSuccess = {
                copy(heared = !heared, location = it)
            }
        ).let { albumsRepository.toggleAlbumHeared(album = it) }


    }
}


