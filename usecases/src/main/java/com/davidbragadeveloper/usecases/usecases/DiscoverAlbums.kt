package com.davidbragadeveloper.usecases.usecases

import arrow.core.Try
import com.davidbragadeveloper.domain.Album
import com.davidbragadeveloper.usecases.repositories.AlbumsRepository

typealias DiscoverAlbums = suspend () -> Try<List<Album>>

fun buildDiscoverAlbumsUseCase(
    repository: AlbumsRepository
): DiscoverAlbums = { repository.dicoverAlbums() }



