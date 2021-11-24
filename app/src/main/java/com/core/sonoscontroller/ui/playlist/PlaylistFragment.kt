package com.core.sonoscontroller.ui.playlist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.core.sonoscontroller.Config
import com.core.sonoscontroller.R
import com.core.sonoscontroller.databinding.MainFragmentBinding
import com.core.sonoscontroller.databinding.PlaylistFragmentBinding
import com.core.sonoscontroller.sonos.SonosDevice
import com.core.sonoscontroller.sonos.listener.SonosEventListener
import com.core.sonoscontroller.sonos.model.*
import com.core.sonoscontroller.ui.playlist.adapter.PlaylistAdapter
import java.util.ArrayList

class PlaylistFragment : Fragment() {

    companion object {
        fun newInstance(device: SonosDevice): PlaylistFragment {
            val fragment = PlaylistFragment()
            fragment.device = device
            return fragment
        }
    }

    private var device: SonosDevice? = null

    private var _binding: PlaylistFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<PlaylistViewModel>()

    private lateinit var adapter: PlaylistAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PlaylistFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        device = Config.device

        device?.let {
            adapter = PlaylistAdapter(it, arrayListOf())
            binding.playlistView.adapter = adapter

            viewModel.getPlaylist(it)
            viewModel.subscribe(it)

        }

        return view
    }


    override fun onResume() {
        super.onResume()

        viewModel.playlistLiveData.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }
    }

}