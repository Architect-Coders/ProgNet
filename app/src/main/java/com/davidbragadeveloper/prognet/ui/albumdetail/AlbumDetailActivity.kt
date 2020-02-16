package com.davidbragadeveloper.prognet.ui.albumdetail

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.davidbragadeveloper.domain.Album
import com.davidbragadeveloper.prognet.R
import com.davidbragadeveloper.prognet.ui.albumdetail.tracklist.TrackListFragment
import com.davidbragadeveloper.prognet.ui.albumdetail.tracklist.TrackListFragment.Companion.tag
import com.davidbragadeveloper.prognet.ui.commons.PermissionRequester
import com.davidbragadeveloper.prognet.ui.commons.loadUrl
import com.davidbragadeveloper.prognet.ui.commons.replace
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

    private val coarsePermissionRequester = PermissionRequester(this, ACCESS_COARSE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_detail)
        viewModel.model.observe(this, Observer(::updateUI))
        addTrackListFragment()
        setUpFabButton()
    }

    private fun updateUI(model: AlbumDetailViewModel.UiModel) =
        when(model){
            AlbumDetailViewModel.UiModel.Loading -> {}
            is AlbumDetailViewModel.UiModel.Content -> {
                with(model.album){
                    titleTextView.text = title
                    mainImageView.loadUrl(url = coverImage)
                    refreshFabButtonState(isHeared = heared)
                }
            }
            AlbumDetailViewModel.UiModel.Error -> {}
        }

    private fun setUpFabButton() {
       hearedFab.backgroundTintList = ColorStateList.valueOf(Color.DKGRAY)
       hearedFab.setOnClickListener{
           coarsePermissionRequester.request {
               if(it){
                   viewModel.hearedFabClicked()
               }
           }
       }
    }

    private fun refreshFabButtonState(isHeared: Boolean){
        hearedFab.backgroundTintList = ColorStateList.valueOf(
            if(isHeared){
                ContextCompat.getColor(this@AlbumDetailActivity,R.color.colorAccent)
            } else {
                Color.DKGRAY
            }
        )
    }


    private fun addTrackListFragment() {
        replace(
            container = R.id.trackListContainer,
            tag = tag
        ) { TrackListFragment.newInstance() }
    }

}
