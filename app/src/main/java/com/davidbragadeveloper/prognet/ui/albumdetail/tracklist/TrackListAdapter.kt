package com.davidbragadeveloper.prognet.ui.albumdetail.tracklist

import android.graphics.Typeface.BOLD
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.davidbragadeveloper.domain.Track
import com.davidbragadeveloper.prognet.R
import com.davidbragadeveloper.prognet.ui.commons.basicDiffUtil
import com.davidbragadeveloper.prognet.ui.commons.inflate
import kotlinx.android.synthetic.main.item_track.view.*

class TrackListAdapter : RecyclerView.Adapter<TrackListAdapter.ViewHolder>() {

    var tracks: List<Track> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.title == new.title && old.position == new.position }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_track, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = tracks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val track = tracks[position]
        holder.bind(track)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val trackTitleTextView: TextView = itemView.trackTitleTextView
        private val trackDurationTextView: TextView = itemView.trackDurationTextView
        private val trackPositionTextView: TextView = itemView.trackPositionTextView

        fun bind(track: Track) {
            trackTitleTextView.setSpannable(title = "Title",value = track.title)
            trackDurationTextView.setSpannable(title = "Duration", value = track.duration)
            trackPositionTextView.setSpannable(title = "Position", value= track.position)
        }


        private fun TextView.setSpannable(title: String, value: String){
            val spannable = SpannableString("$title: $value").apply {
                setSpan(
                    StyleSpan(BOLD),
                0,
                    title.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            text = spannable

        }

    }
}
