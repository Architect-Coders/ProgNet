package com.davidbragadeveloper.prognet.ui.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidbragadeveloper.domain.Album
import com.davidbragadeveloper.prognet.ui.commons.Event
import com.davidbragadeveloper.usecases.usecases.DiscoverAlbums
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private inline val discoverAlbums: DiscoverAlbums,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
    ) : ViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    private val _navigation = MutableLiveData<Event<Album>>()
    val navigation: LiveData<Event<Album>> = _navigation

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val albums: List<Album>) : UiModel()
        object Error : UiModel()
    }




    fun onAlbumClicked(album: Album) {
        _navigation.value = Event(album)
    }

    init {
        _model.value = UiModel.Loading
    }

    fun refresh() {
        viewModelScope.launch(dispatcher) {
            val albums  = (_model.value as? UiModel.Content)?.albums
            if(albums == null) {
                _model.value = UiModel.Loading
                _model.value =discoverAlbums.invoke().fold(
                    ifFailure = {
                        UiModel.Error
                    },
                    ifSuccess = {
                        UiModel.Content(it)
                    }
                )
            } else{
                _model.value = UiModel.Content(albums)
            }
        }
    }
}
