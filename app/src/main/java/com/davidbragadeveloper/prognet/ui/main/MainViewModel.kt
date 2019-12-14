package com.davidbragadeveloper.prognet.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidbragadeveloper.prognet.model.AlbumsRepository
import com.davidbragadeveloper.prognet.model.ProgRockAlbum
import kotlinx.coroutines.launch

class MainViewModel(private val albumsRepository: AlbumsRepository) : ViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val albums: List<ProgRockAlbum>) : UiModel()
        object Error : UiModel()
        class Navigation(val album: ProgRockAlbum) : UiModel()
    }

    init {
        refresh()
    }

    fun onAlbumClicked(album: ProgRockAlbum) {
        _model.value = UiModel.Navigation(album)
    }

    private fun refresh() {
        viewModelScope.launch {
            _model.value = UiModel.Loading
            _model.value =
                albumsRepository.discoverProgAlbums().fold(
                    ifFailure = {
                        UiModel.Error
                    },
                    ifSuccess = {
                        UiModel.Content(it.results)
                    }
            )
        }
    }
}
