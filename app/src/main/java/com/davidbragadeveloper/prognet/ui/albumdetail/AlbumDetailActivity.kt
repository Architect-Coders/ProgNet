package com.davidbragadeveloper.prognet.ui.albumdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.davidbragadeveloper.domain.Album
import com.davidbragadeveloper.prognet.R
import com.davidbragadeveloper.prognet.ui.albumdetail.tracklist.TrackListFragment
import com.davidbragadeveloper.prognet.ui.albumdetail.tracklist.TrackListFragment.Companion.tag
import com.davidbragadeveloper.prognet.ui.commons.loadUrl
import kotlinx.android.synthetic.main.activity_album_detail.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AlbumDetailActivity : AppCompatActivity() {

    companion object {
        const val ALBUM = "DetailActivity:album"
    }

    private val viewModel: AlbumDetailViewModel by currentScope.viewModel(this){
        parametersOf(intent.getSerializableExtra(ALBUM) as Album)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_detail)
        viewModel.model.observe(this, Observer(::updateUI))
        val fragment = supportFragmentManager.findFragmentByTag(tag) ?: TrackListFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.trackListContainer, fragment, tag)
            .commit()
    }

    private fun updateUI(model: AlbumDetailViewModel.UiModel) =
        when(model){
            AlbumDetailViewModel.UiModel.Loading -> {}
            is AlbumDetailViewModel.UiModel.Content -> {
                with(model.album){
                    titleTextView.text = title
                    mainImageView.loadUrl(url = coverImage)
                }
            }
            AlbumDetailViewModel.UiModel.Error -> {}
        }
}
