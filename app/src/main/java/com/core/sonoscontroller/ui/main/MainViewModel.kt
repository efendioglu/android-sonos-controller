package com.core.sonoscontroller.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.core.sonoscontroller.sonos.SonosDevice
import com.core.sonoscontroller.sonos.SonosDiscovery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    val devicesLiveData = MutableLiveData<List<SonosDevice>>()

    fun discoverDevices() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val devices = SonosDiscovery.discover()
                devicesLiveData.postValue(devices)
            }
        }
    }
}