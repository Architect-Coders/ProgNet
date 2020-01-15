package com.davidbragadeveloper.prognet.ui.main

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.davidbragadeveloper.domain.Album
import com.davidbragadeveloper.prognet.R
import com.davidbragadeveloper.prognet.ui.commons.basicDiffUtil
import com.davidbragadeveloper.prognet.ui.commons.inflate
import com.davidbragadeveloper.prognet.ui.commons.loadUrl
import kotlinx.android.synthetic.main.item_album.view.*

class AlbumsAdapter(private inline val listener: (Album) -> Unit) : RecyclerView.Adapter<AlbumsAdapter.ViewHolder>() {

    var albums: List<Album> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_album, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = albums.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = albums[position]
        holder.bind(album)
        holder.itemView.setOnClickListener { listener(album) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val titleTextView: TextView = itemView.titleTextView
        private val yearTextView: TextView = itemView.yearTextView
        private val countryTextView: TextView = itemView.countryTextView
        private val coverImageView: ImageView = itemView.coverImageView

        fun bind(album: Album) {
            titleTextView.text = album.title
            yearTextView.text = album.year
            countryTextView.text = album.country
            coverImageView.loadUrl(album.coverImage)
        }

    }
}
