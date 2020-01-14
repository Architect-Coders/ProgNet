package com.davidbragadeveloper.usecases.usecases

import arrow.core.Try
import com.davidbragadeveloper.domain.Album
import com.davidbragadeveloper.usecases.repositories.AlbumsRepository

typealias DiscoverProgAlbums = suspend () -> Try<List<Album>>

fun buildDiscoverProgAlbums(
    repository: AlbumsRepository
): DiscoverProgAlbums = { repository.dicoverProgAlbums() }
