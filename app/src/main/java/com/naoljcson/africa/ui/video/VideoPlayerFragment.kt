package com.naoljcson.africa.ui.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.naoljcson.africa.databinding.FragmentVideoPlayerBinding
import com.naoljcson.africa.utils.hide
import com.naoljcson.africa.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VideoPlayerFragment : Fragment() {
    private val viewModel: VideoViewModel by viewModels()
    private var _binding: FragmentVideoPlayerBinding? = null
    private val binding get() = _binding!!
    private lateinit var mediaController: MediaController
    private lateinit var id: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoPlayerBinding.inflate(layoutInflater, container, false)
        with(binding.shimmerVideoContainer){
            show()
            startShimmer()
        }
        mediaController = MediaController(requireContext())
        mediaController.setAnchorView(binding.videoView)
        id = arguments?.let { VideoPlayerFragmentArgs.fromBundle(it).id }.toString()
        viewModel.getVideo(id)
        observeVideo()
        return binding.root
    }

    private fun observeVideo() {
        lifecycleScope.launch {
            delay(300)
            viewModel.ldVideo.collect { video ->
                with(binding.shimmerVideoContainer) {
                    stopShimmer()
                    hide()
                }
                with(binding.videoView) {
                    show()
                    setVideoURI(video?.videoUri)
                    start()
                    setMediaController(mediaController)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = VideoPlayerFragment()
    }
}