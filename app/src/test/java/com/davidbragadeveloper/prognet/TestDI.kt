package com.davidbragadeveloper.prognet

import arrow.core.Try
import com.davidbragadeveloper.data.repository.PermissionChecker
import com.davidbragadeveloper.data.source.AlbumLocalDataSource
import com.davidbragadeveloper.data.source.AlbumRemoteDataSource
import com.davidbragadeveloper.data.source.LocationDataSource
import com.davidbragadeveloper.domain.Album
import com.davidbragadeveloper.domain.ProgNetLocation
import com.davidbragadeveloper.testshared.mockedAlbum
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun initMockedDi(vararg modules: Module) {
    startKoin {
        modules(listOf(mockedAppModule, dataModule) + modules)
    }
}

private val mockedAppModule = module {
    single(named("apiKey")) { "123456" }
    single(named("apiSecret")){ "98765432" }
    single<AlbumLocalDataSource> { FakeLocalDataSource() }
    single<AlbumRemoteDataSource> { FakeRemoteDataSource() }
    single<LocationDataSource> { FakeLocationDataSource() }
    single<PermissionChecker> { FakePermissionChecker() }
    single { Dispatchers.Unconfined }
}

val defaultFakeAlbums = listOf(
    mockedAlbum.copy(1),
    mockedAlbum.copy(2),
    mockedAlbum.copy(3),
    mockedAlbum.copy(4)
)

class FakeLocalDataSource : AlbumLocalDataSource{

    var albums: List<Album> = emptyList()
    override suspend fun isEmpty(): Try<Boolean> = Try{ albums.isEmpty() }

    override suspend fun saveAlbums(albums: List<Album>) { this.albums = albums }

    override suspend fun getAlbums(): Try<List<Album>> = Try{ albums }

    override suspend fun findById(id: Long): Try<Album> = Try{ albums.first { it.id == id }}

    override suspend fun updateHeared(album: Album): Try<Boolean> = Try{
        albums = albums.filterNot { album.id == it.id } + album
        !album.heared
    }

    override suspend fun saveNotSavedAlbums(albums: List<Album>) { this.albums = albums }

}

class FakeRemoteDataSource : AlbumRemoteDataSource {

    var albums = defaultFakeAlbums

    override suspend fun discoverAlbums(apiKey: String, apiSecret: String): Try<List<Album>> = Try{ albums }

    override suspend fun getAlbumById(albumId: Long): Try<Album> = Try{ albums.first{ it.id == albumId} }
}


class FakeLocationDataSource : LocationDataSource {
    var location = ProgNetLocation(latitude = 3.5, longitude = 10.2)

    override suspend fun findLastLocation(): Try<ProgNetLocation?>  = Try{ location }
}

class FakePermissionChecker : PermissionChecker {
    var permissionGranted = true

    override suspend fun check(permission: PermissionChecker.Permission): Boolean =
        permissionGranted
}
