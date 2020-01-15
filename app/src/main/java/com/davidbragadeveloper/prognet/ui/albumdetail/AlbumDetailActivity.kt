package com.davidbragadeveloper.prognet.ui.albumdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.davidbragadeveloper.prognet.R

class AlbumDetailActivity : AppCompatActivity() {

    companion object {
        const val ALBUM = "DetailActivity:album"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_detail)
    }
}
