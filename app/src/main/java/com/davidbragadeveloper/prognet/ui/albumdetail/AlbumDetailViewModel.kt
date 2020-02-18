package com.davidbragadeveloper.prognet.ui.albumdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidbragadeveloper.domain.Album
import com.davidbragadeveloper.prognet.ui.main.MainViewModel
import com.davidbragadeveloper.usecases.usecases.GetAlbumById
import com.davidbragadeveloper.usecases.usecases.ToggleAlbumHeared
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlbumDetailViewModel(
    private val shortAlbumData: Album,
    private inline val getAlbumById: GetAlbumById,
    private inline val toggleAlbumHeared: ToggleAlbumHeared,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
): ViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            return _model
        }

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val album: Album) : UiModel()
        object Error : UiModel()
    }

    init {
        _model.value = UiModel.Loading
    }

    fun refresh() =
        viewModelScope.launch(dispatcher) {
            _model.value = UiModel.Loading
            _model.value =
                getAlbumById(shortAlbumData.id).fold(
                    ifFailure = {
                        UiModel.Error
                    },
                    ifSuccess = {
                        UiModel.Content(it)
                    }
                )
        }

    fun hearedFabClicked() =
        when (val modelValue = model.value) {
            is UiModel.Content -> {
                viewModelScope.launch(dispatcher) {
                    toggleAlbumHeared(modelValue.album)
                        .fold(
                            ifFailure = {},
                            ifSuccess = { refresh() }
                        )
                }
            }
            else -> {}
        }

}


