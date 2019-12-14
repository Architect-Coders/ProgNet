package com.davidbragadeveloper.prognet.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davidbragadeveloper.prognet.R
import com.davidbragadeveloper.prognet.model.ProgRockAlbum
import com.davidbragadeveloper.prognet.ui.commons.basicDiffUtil
import com.davidbragadeveloper.prognet.ui.commons.inflate
import com.davidbragadeveloper.prognet.ui.commons.loadUrl
import kotlinx.android.synthetic.main.item_album.view.*

class AlbumsAdapter(private val listener: (ProgRockAlbum) -> Unit) : RecyclerView.Adapter<AlbumsAdapter.ViewHolder>() {

    var albums: List<ProgRockAlbum> by basicDiffUtil(
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
        fun bind(album: ProgRockAlbum) {
            itemView.titleTextView.text = album.title
            itemView.yearTextView.text = album.year
            itemView.countryTextView.text = album.country
            itemView.coverImageView.loadUrl(album.coverImage)
        }
    }
}
