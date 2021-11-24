package com.core.sonoscontroller.ui.playlist.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.core.sonoscontroller.R
import com.core.sonoscontroller.databinding.ItemlistTrackBinding
import com.core.sonoscontroller.sonos.SonosDevice
import com.core.sonoscontroller.sonos.model.PlayState
import com.core.sonoscontroller.sonos.model.TrackMetadata
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlaylistAdapter(val device: SonosDevice, var tracks: List<TrackMetadata>): RecyclerView.Adapter<PlaylistAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.itemlist_track, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            bind(tracks[position])
        }
    }

    override fun getItemCount(): Int = tracks.size

    fun setData(playlist: List<TrackMetadata>) {
        this.tracks = playlist
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemlistTrackBinding.bind(itemView)

        fun bind(track: TrackMetadata) {
            binding.nameView.text = track.title

            CoroutineScope(Dispatchers.IO).launch {
                val trackInfo = device.currentTrackInfo
                val playState = device.playState

                if (playState == PlayState.PLAYING && track.uri == trackInfo.metadata.uri) {
                    binding.playButton.setImageResource(android.R.drawable.ic_media_pause)
                } else {
                    binding.playButton.setImageResource(android.R.drawable.ic_media_play)
                }
            }

            binding.playButton.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val playState = device.playState
                    val trackInfo = device.currentTrackInfo
                    if (playState == PlayState.PLAYING && track.uri == trackInfo.metadata.uri) {
                        device.pause()
                    } else {
                        device.playUri(track.uri, track)
                    }
                }
            }

            binding.nextButton.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    device.next()
                }
            }

            binding.prevButton.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    device.previous()
                }
            }
        }
    }
}