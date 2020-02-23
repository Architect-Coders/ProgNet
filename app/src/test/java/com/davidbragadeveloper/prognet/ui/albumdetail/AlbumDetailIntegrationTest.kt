package com.davidbragadeveloper.prognet.ui.albumdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.getOrDefault
import arrow.core.getOrElse
import arrow.syntax.function.curried
import com.davidbragadeveloper.data.source.AlbumLocalDataSource
import com.davidbragadeveloper.domain.Album
import com.davidbragadeveloper.prognet.FakeLocalDataSource
import com.davidbragadeveloper.prognet.defaultFakeAlbums
import com.davidbragadeveloper.prognet.initMockedDi
import com.davidbragadeveloper.testshared.mockedAlbum
import com.davidbragadeveloper.usecases.usecases.buildGetAlbumByIdUseCase
import com.davidbragadeveloper.usecases.usecases.buildToggleAlbumHearedUseCase
import junit.framework.Assert
import junit.framework.Assert.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.CountDownLatch

@RunWith(MockitoJUnitRunner::class)
class AlbumDetailIntegrationTests : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var vm: AlbumDetailViewModel
    private lateinit var localDataSource: FakeLocalDataSource

    @Before
    fun setUp() {

        val vmModule = module {

            factory(named("getAlbumById")) {  buildGetAlbumByIdUseCase(get()) }
            factory(named("toggleAlbumHeared")) {  buildToggleAlbumHearedUseCase(get(), get()) }
            factory { (album: Album) -> AlbumDetailViewModel(
                shortAlbumData = album,
                getAlbumById = get(named("getAlbumById")),
                toggleAlbumHeared = get(named("toggleAlbumHeared")),
                dispatcher = get())
            }
        }

        initMockedDi(vmModule)
        vm = get { parametersOf(mockedAlbum.copy(id=1)) }

        localDataSource = get<AlbumLocalDataSource>() as FakeLocalDataSource
        localDataSource.albums = defaultFakeAlbums
    }

    @Test
    fun `observing LiveData finds the movie`() {
        val countDownLatch = CountDownLatch (1)
        vm.refresh()
        vm.model.observeForever{
            if(it is AlbumDetailViewModel.UiModel.Content){
                assertEquals( mockedAlbum.copy(id=1), it.album)
                countDownLatch.countDown()
            }
        }
        countDownLatch.await()
    }

    @Test(timeout = 1000)
    fun `favorite is updated in local data source`() {
        val countDownLatch = CountDownLatch(2)

        vm.refresh()
        vm.model.observeForever{
            if(it is AlbumDetailViewModel.UiModel.Content){
                if(countDownLatch.count == 2L) {
                    println("First: ${it.album}")
                    vm.hearedFabClicked()
                    countDownLatch.countDown()
                }else if (countDownLatch.count == 1L) {
                    runBlocking {
                        assertFalse(localDataSource.findById(1).fold(
                            ifSuccess = {
                                println("Second: $it")
                                it.heared
                            },
                            ifFailure = {
                               true
                            }
                        ))
                    }
                    countDownLatch.countDown()
                }
            }
        }
        countDownLatch.await()


    }
}