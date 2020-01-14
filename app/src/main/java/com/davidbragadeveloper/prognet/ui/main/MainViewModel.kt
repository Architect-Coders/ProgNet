package com.davidbragadeveloper.prognet.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidbragadeveloper.domain.Album
import com.davidbragadeveloper.usecases.usecases.DiscoverProgAlbums
import kotlinx.coroutines.launch

class MainViewModel(private val discoverProgAlbums: DiscoverProgAlbums) : ViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val albums: List<Album>) : UiModel()
        object Error : UiModel()
        class Navigation(val album: Album) : UiModel()
    }

    init {
        refresh()
    }

    fun onAlbumClicked(album: Album) {
        _model.value = UiModel.Navigation(album)
    }

    private fun refresh() {
        viewModelScope.launch {
            _model.value = UiModel.Loading
            _model.value =
                discoverProgAlbums().fold(
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
