package com.davidbragadeveloper.prognet.ui.main


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.Try
import com.davidbragadeveloper.testshared.mockedSimpleAlbum
import com.davidbragadeveloper.usecases.usecases.DiscoverAlbums
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch



class MainViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val albums = listOf(mockedSimpleAlbum.copy(id=2))

    private lateinit var discoverAlbums: DiscoverAlbums

    private lateinit var vm: MainViewModel

    @Before
    fun setUp() {
        discoverAlbums = { Try.Success(albums)}
        vm = MainViewModel(discoverAlbums, Dispatchers.Unconfined)
    }


    @Test(timeout= 1000)
    fun `should show loading, before requesting discoverAlbums`() {
        val countDownLatch = CountDownLatch(1)
        vm.model.observeForever{
            assertEquals(MainViewModel.UiModel.Loading, it)
            countDownLatch.countDown()
        }

        countDownLatch.await()
    }

    @Test(timeout = 1000)
    fun `should discoverAlbums called when refresh is invoked`() {
        val countDownLatch = CountDownLatch(1)
        vm.refresh()
        vm.model.observeForever{
            if(it is MainViewModel.UiModel.Content){
                assertEquals(albums, it.albums)
                countDownLatch.countDown()
            }
        }

        countDownLatch.await()

    }
}

