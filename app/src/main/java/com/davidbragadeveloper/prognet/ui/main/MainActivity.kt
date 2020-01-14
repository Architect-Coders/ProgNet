package com.davidbragadeveloper.prognet.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.davidbragadeveloper.data.repository.AlbumsDataRepository
import com.davidbragadeveloper.prognet.BuildConfig
import com.davidbragadeveloper.prognet.R
import com.davidbragadeveloper.prognet.data.remote.DiscogsDataSource
import com.davidbragadeveloper.prognet.ui.commons.getViewModel
import com.davidbragadeveloper.usecases.usecases.buildDicoverProgAlbums
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: AlbumsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = getViewModel {
            MainViewModel(discoverProgAlbums = buildDicoverProgAlbums(
                repository = AlbumsDataRepository(
                    apiSecret = BuildConfig.discogsApiSecret,
                    apiKey = BuildConfig.discogsApiKey,
                    dataSource = DiscogsDataSource())
            )) }

        adapter = AlbumsAdapter(viewModel::onAlbumClicked)
        recycler.adapter = adapter
        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: MainViewModel.UiModel) {

        progress.visibility = if (model is MainViewModel.UiModel.Loading) View.VISIBLE else View.GONE
        recycler.visibility = if (model is MainViewModel.UiModel.Error) View.GONE else View.VISIBLE
        errorScreen.visibility = if (model is MainViewModel.UiModel.Error) View.VISIBLE else View.GONE

        when (model) {
            is MainViewModel.UiModel.Content -> adapter.albums = model.albums
            is MainViewModel.UiModel.Navigation -> { } // TODO: Update when database
            /*startActivity<AlbumDetailActivity> {
                putExtra(AlbumDetailActivity.ALBUM, model.album)
            }*/
        }
    }
}
