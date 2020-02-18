package com.davidbragadeveloper.prognet.ui.albumdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.Try
import com.davidbragadeveloper.testshared.mockedAlbum
import com.davidbragadeveloper.testshared.mockedSimpleAlbum
import com.davidbragadeveloper.usecases.usecases.GetAlbumById
import com.davidbragadeveloper.usecases.usecases.ToggleAlbumHeared
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
class AlbumDetailViewModelTest {
    @get:Rule val rule = InstantTaskExecutorRule()

    private lateinit var toggleAlbumHeared: ToggleAlbumHeared
    private lateinit var getAlbumById: GetAlbumById
    private val album = mockedAlbum.copy(id = 2)
    private lateinit var vm: AlbumDetailViewModel

    @Before
    fun setUp() {
        toggleAlbumHeared = {album -> Try.Success(!album.heared)}
        getAlbumById = {id -> Try.Success(album)}
        vm = AlbumDetailViewModel(
            shortAlbumData = mockedSimpleAlbum.copy(id=2),
            getAlbumById = getAlbumById,
            toggleAlbumHeared = toggleAlbumHeared,
            dispatcher = Dispatchers.Unconfined
        )
    }


    @Test(timeout = 1000)
    fun `should show loading, before requesting discoverAlbums`() {
        val countDownLatch = CountDownLatch(1)
        vm.model.observeForever{
            assertEquals(AlbumDetailViewModel.UiModel.Loading, it)
            countDownLatch.countDown()
        }
        countDownLatch.await()
    }

    @Test(timeout = 1000)
    fun `should getAlbumById called when refresh is invoked`() {
        val countDownLatch = CountDownLatch(1)

        vm.refresh()

        vm.model.observeForever{
            if(it is AlbumDetailViewModel.UiModel.Content){
                assertEquals(album, it.album)
                countDownLatch.countDown()
            }
        }
        countDownLatch.await()
    }

    @Test(timeout = 1000)
    fun `should toggleAlbumHered use case is invoked when heared clicked`() {
        val countDownLatch = CountDownLatch(2)
        vm.refresh()
        vm.model.observeForever{
            if(it is AlbumDetailViewModel.UiModel.Content){
                if(countDownLatch.count == 2L){
                    vm.hearedFabClicked()
                    countDownLatch.countDown()
                }else if(countDownLatch.count == 1L){
                    assertEquals(album, it.album)
                    countDownLatch.countDown()
                }
            }
        }
        countDownLatch.await()


    }
}
