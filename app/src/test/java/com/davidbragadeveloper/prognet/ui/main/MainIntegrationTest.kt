package com.davidbragadeveloper.prognet.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import arrow.core.Try
import com.davidbragadeveloper.data.source.AlbumLocalDataSource
import com.davidbragadeveloper.domain.Album
import com.davidbragadeveloper.prognet.FakeLocalDataSource
import com.davidbragadeveloper.prognet.defaultFakeAlbums
import com.davidbragadeveloper.prognet.initMockedDi
import com.davidbragadeveloper.prognet.ui.albumdetail.AlbumDetailViewModel
import com.davidbragadeveloper.usecases.usecases.buildDiscoverAlbumsUseCase
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.CountDownLatch

@RunWith(MockitoJUnitRunner::class)
class MainIntegrationTests : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var vm: MainViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { buildDiscoverAlbumsUseCase(get()) }
            factory { MainViewModel(get(), get()) }
        }

        initMockedDi(vmModule)
        vm = get()
    }

    @Test(timeout = 1000)
    fun `should data loaded from server`() {
        val countDownLatch = CountDownLatch(1)
        vm.refresh()
        vm.model.observeForever{
            if(it is MainViewModel.UiModel.Content){
                assertEquals(defaultFakeAlbums, it.albums)
                countDownLatch.countDown()
            }
        }
        countDownLatch.await()
    }

}
