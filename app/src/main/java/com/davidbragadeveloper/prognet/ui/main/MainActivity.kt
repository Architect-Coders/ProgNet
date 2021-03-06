package com.davidbragadeveloper.prognet.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.davidbragadeveloper.domain.Album
import com.davidbragadeveloper.prognet.R
import com.davidbragadeveloper.prognet.ui.albumdetail.AlbumDetailActivity
import com.davidbragadeveloper.prognet.ui.commons.startActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by currentScope.viewModel(this)
    private lateinit var adapter: AlbumsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.refresh()
        setContentView(R.layout.activity_main)
        adapter = AlbumsAdapter(viewModel::onAlbumClicked)
        recycler.adapter = adapter
        viewModel.model.observe(this, Observer(::updateUi))
        viewModel.navigation.observe(this, Observer{
            it.getContentIfNotHandled()?.let(::navigateToDetail)
        })
    }

    private fun navigateToDetail(album: Album){
        startActivity<AlbumDetailActivity> {
            putExtra(AlbumDetailActivity.ALBUM,album)
        }
    }

    private fun updateUi(model: MainViewModel.UiModel) {

        progress.visibility = if (model is MainViewModel.UiModel.Loading) View.VISIBLE else View.GONE
        recycler.visibility = if (model is MainViewModel.UiModel.Error) View.GONE else View.VISIBLE
        errorScreen.visibility = if (model is MainViewModel.UiModel.Error) View.VISIBLE else View.GONE

        when (model) {
            is MainViewModel.UiModel.Content ->{ adapter.albums = model.albums }
        }
    }
}
