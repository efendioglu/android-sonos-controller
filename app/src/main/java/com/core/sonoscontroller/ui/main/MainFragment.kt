package com.core.sonoscontroller.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.core.sonoscontroller.R
import com.core.sonoscontroller.databinding.MainFragmentBinding
import com.core.sonoscontroller.sonos.SonosDevice
import com.core.sonoscontroller.ui.main.adapter.PlayersAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MainViewModel>()

    private var adapter: PlayersAdapter = PlayersAdapter(arrayListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.playerListView.adapter = adapter

        return view
    }

    override fun onResume() {
        super.onResume()
        viewModel.discoverDevices()

        viewModel.devicesLiveData.observe(viewLifecycleOwner, { devices ->
            adapter.setData(devices)
        })
    }

}