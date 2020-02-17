package com.davidbragadeveloper.data.repository

import arrow.core.Try
import com.davidbragadeveloper.data.source.AlbumLocalDataSource
import com.davidbragadeveloper.data.source.AlbumRemoteDataSource
import com.davidbragadeveloper.domain.Track
import com.davidbragadeveloper.testshared.mockedAlbum
import com.davidbragadeveloper.testshared.mockedSimpleAlbum
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AlbumsDataRepositoryTest {

    @Mock
    lateinit var localDataSource: AlbumLocalDataSource

    @Mock
    lateinit var remoteDataSource: AlbumRemoteDataSource


    lateinit var albumsRepository: AlbumsDataRepository

    val apiKey = "lksjlfkjalñfas"
    val apiSecret = "lkjfasñdfjaslñkdf"

    @Before
    fun setUp() {
        albumsRepository = AlbumsDataRepository(
            remoteDataSource= remoteDataSource,
            localDataSource = localDataSource,
            apiKey = apiKey,
            apiSecret = apiSecret
        )
    }

    @Test
    fun `should discoverAlbums save not saved Albums data in dataSource`(){
        runBlocking {

            val remoteAlbums = listOf(mockedSimpleAlbum.copy(id=2))
            val remoteAlbumsResponse = Try{ remoteAlbums }
            whenever(remoteDataSource.discoverAlbums(any(), any())).thenReturn(remoteAlbumsResponse)

            albumsRepository.dicoverAlbums()

            verify(localDataSource).saveNotSavedAlbums(remoteAlbums)
        }
    }

    @Test
    fun `should discoverAlbums calls remoteDataSource`(){
        runBlocking {
            val remoteAlbums = listOf(mockedSimpleAlbum.copy(id=2))
            val remoteAlbumsResponse = Try{ remoteAlbums }
            whenever(remoteDataSource.discoverAlbums(any(), any())).thenReturn(remoteAlbumsResponse)

            albumsRepository.dicoverAlbums()

            verify(remoteDataSource).discoverAlbums(any(), any())
        }
    }

    @Test
    fun `should getAlbumById calls only local data source if tracks are setted`() {
        runBlocking {

            val album = mockedAlbum.copy(id = 5)
            whenever(localDataSource.findById(5)).thenReturn(Try{album})


            val result = albumsRepository.getAlbumById(5)

            assertEquals(Try{album}, result)
        }
    }

    @Test
    fun `should getAlbumById calls remoteData source when there is an exception`(){
        runBlocking {

            val album = mockedAlbum
            whenever(localDataSource.findById(5)).thenReturn(Try.Failure(Exception()))
            whenever(remoteDataSource.getAlbumById(5)).thenReturn(Try{album})

            val result = albumsRepository.getAlbumById(5)

            verify(remoteDataSource).getAlbumById(5)
        }

    }


    @Test
    fun `should getAlbumById calls remoteData source when there is no tracksSetted`(){
        runBlocking {

            val album = mockedSimpleAlbum.copy(id=5)

            whenever(localDataSource.findById(5)).thenReturn(Try.Success(album))
            whenever(remoteDataSource.getAlbumById(5)).thenReturn(Try{album})

            val result = albumsRepository.getAlbumById(5)

            verify(remoteDataSource).getAlbumById(5)
        }
    }

    @Test
    fun `should getAlbumById merges localDataSource response with remoteDataSource response when remoteDataSource is called`(){
        runBlocking {
            val album = mockedSimpleAlbum.copy(id = 5)
            whenever(localDataSource.findById(5)).thenReturn(Try.Success(album))
            whenever(remoteDataSource.getAlbumById(5)).thenReturn(Try { mockedAlbum.copy(id = 5) })

            val result = albumsRepository.getAlbumById(5)

            assertEquals(mockedSimpleAlbum.country, result.fold(
                ifFailure = { "fkljasdjflñas" },
                ifSuccess = { it.country }
            ))
            assertEquals(mockedAlbum.tracks, result.fold(
                ifFailure = { listOf<Track>() },
                ifSuccess = { it.tracks }
            ))
        }
    }


    @Test
    fun `should getAlbumById calla save albums with mergedResult`(){
        runBlocking {
            val localAlbum = mockedSimpleAlbum.copy(id = 5)

            whenever(localDataSource.findById(5)).thenReturn(Try.Success(localAlbum))

            val remoteAlbum =  mockedAlbum.copy(id = 5)

            whenever(remoteDataSource.getAlbumById(5)).thenReturn(Try { remoteAlbum})

            val result = albumsRepository.getAlbumById(5)

            verify(localDataSource).saveAlbums(any())
            verify(localDataSource, times(0)).saveAlbums(listOf(localAlbum))
            verify(localDataSource, times(0)).saveAlbums(listOf(remoteAlbum))
        }

    }

    @Test
    fun `should toggleAlbumHeared calls remoteDataSource`(){
        runBlocking {
            val album = mockedSimpleAlbum.copy(id=2)

            albumsRepository.toggleAlbumHeared(album)

            verify(localDataSource).updateHeared(album = album)
        }
    }




}
