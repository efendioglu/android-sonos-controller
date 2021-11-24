package com.core.sonoscontroller.ui.main.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.core.sonoscontroller.Config
import com.core.sonoscontroller.R
import com.core.sonoscontroller.databinding.ItemlistPlayerBinding
import com.core.sonoscontroller.sonos.SonosDevice
import com.core.sonoscontroller.ui.playlist.PlaylistFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlayersAdapter(private var players: List<SonosDevice>) : RecyclerView.Adapter<PlayersAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater
        .from(parent.context).inflate(R.layout.itemlist_player, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            bind(players[position])
        }
    }

    override fun getItemCount(): Int = players.size

    fun setData(players: List<SonosDevice>) {
        this.players = players
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ItemlistPlayerBinding.bind(itemView)

        fun bind(device: SonosDevice) {
            CoroutineScope(Dispatchers.IO).launch {
                device.playState
                val name = device.deviceNameCached
                val roomName = device.roomNameCached
                withContext(Dispatchers.Main) {
                    binding.nameView.text = name
                    binding.roomView.text = roomName

                    itemView.setOnClickListener {
                        Config.device = device
                        itemView.findNavController().navigate(R.id.action_mainFragment_to_playlistFragment)
                    }
                }


            }
        }
    }
}