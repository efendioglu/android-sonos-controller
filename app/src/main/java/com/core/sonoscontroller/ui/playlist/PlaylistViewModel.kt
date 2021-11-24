package com.core.sonoscontroller.ui.playlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.sonoscontroller.sonos.SonosDevice
import com.core.sonoscontroller.sonos.listener.SonosEventAdapter
import com.core.sonoscontroller.sonos.listener.SonosEventListener
import com.core.sonoscontroller.sonos.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.ArrayList

class PlaylistViewModel : ViewModel() {
    val playlistLiveData = MutableLiveData<List<TrackMetadata>>()

    fun getPlaylist(device: SonosDevice) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val playlist = device.getPlaylist(0, 50)
                playlistLiveData.postValue(playlist)
            }
        }
    }

    fun subscribe(device: SonosDevice) {
        viewModelScope.launch(Dispatchers.IO) {
            device.registerSonosEventListener(object : SonosEventAdapter() {
                override fun playStateChanged(playState: PlayState?) {
                    playlistLiveData.postValue(playlistLiveData.value)
                }
            })
        }
    }

}