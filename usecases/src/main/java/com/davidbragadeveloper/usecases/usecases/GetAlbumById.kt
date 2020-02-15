package com.davidbragadeveloper.usecases.usecases

import arrow.core.Try
import com.davidbragadeveloper.domain.Album
import com.davidbragadeveloper.usecases.repositories.AlbumsRepository

typealias GetAlbumById = suspend (Long) -> Try<Album>

fun buildGetAlbumByIdUseCase(
    repository: AlbumsRepository
): GetAlbumById = {albumId -> repository.getAlbumById(albumId = albumId)}

