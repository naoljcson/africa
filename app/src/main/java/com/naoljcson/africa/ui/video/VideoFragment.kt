package com.naoljcson.africa.ui.video

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.naoljcson.africa.R
import com.naoljcson.africa.adapter.VideoListAdapter
import com.naoljcson.africa.data.model.Video
import com.naoljcson.africa.databinding.FragmentVideoBinding
import com.naoljcson.africa.utils.OnClickListener
import com.naoljcson.africa.utils.hide
import com.naoljcson.africa.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoFragment : Fragment(), OnClickListener {

    companion object {
        fun newInstance() = VideoFragment()
    }

    private val viewModel: VideoViewModel by viewModels()
    private var _binding: FragmentVideoBinding? = null
    private val binding get() = _binding!!
    private lateinit var videoListAdapter: VideoListAdapter
    private val videoList = mutableListOf<Video>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoBinding.inflate(layoutInflater, container, false)
        viewModel.getVideos()
        observeVideos()
        videoListAdapter = VideoListAdapter(videoList, this)
        with(binding.rvVideoList) {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = videoListAdapter
        }
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeVideos() {
        lifecycleScope.launchWhenStarted {
            viewModel.ldVideos.collect { vidoes ->
                if (vidoes != null) {
                    videoList.addAll(vidoes)
                    with(binding) {
                        with(shimmerVideoContainer) {
                            stopShimmer()
                            hide()
                        }
                        rvVideoList.show()
                    }
                    Log.i("VideoFragment", "videoList $vidoes")
                    videoListAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(position: Int) {
        val bundle = Bundle()
        bundle.putString("id", videoList[position].id)
        findNavController().navigate(R.id.action_nav_video_dest_to_videoPlayerFragment, bundle)
    }

}