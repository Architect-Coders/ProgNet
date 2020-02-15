package com.davidbragadeveloper.prognet.ui.albumdetail.tracklist


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.observe

import com.davidbragadeveloper.prognet.R
import com.davidbragadeveloper.prognet.ui.albumdetail.AlbumDetailViewModel
import com.davidbragadeveloper.prognet.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_track_list.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class TrackListFragment : Fragment() {

    private val viewModel: AlbumDetailViewModel by sharedViewModel()
    private lateinit var adapter: TrackListAdapter

    companion object {
        const val tag = "TackListFragment"
        @JvmStatic
        fun newInstance() = TrackListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_track_list, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = TrackListAdapter()
        trackRecycler.adapter = adapter
        viewModel.model.observe(viewLifecycleOwner, Observer(::updateUi))
    }

    private fun updateUi(model: AlbumDetailViewModel.UiModel) = when (model){
        AlbumDetailViewModel.UiModel.Loading -> {}
        is AlbumDetailViewModel.UiModel.Content -> {
            adapter.tracks = model.album.tracks
        }
        AlbumDetailViewModel.UiModel.Error -> {}
    }


}


