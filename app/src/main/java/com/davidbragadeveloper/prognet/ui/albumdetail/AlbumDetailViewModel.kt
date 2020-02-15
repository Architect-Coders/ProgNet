package com.davidbragadeveloper.prognet.ui.albumdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidbragadeveloper.domain.Album
import com.davidbragadeveloper.usecases.usecases.GetAlbumById
import kotlinx.coroutines.launch

class AlbumDetailViewModel(
    private val shortAlbumData: Album,
    private val getAlbumById: GetAlbumById
): ViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val album: Album) : UiModel()
        object Error : UiModel()
    }

    init {
        refresh()
    }

    private fun refresh() {
        viewModelScope.launch {
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
    }
}
