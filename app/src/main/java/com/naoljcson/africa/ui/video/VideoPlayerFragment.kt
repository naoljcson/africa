package com.naoljcson.africa.ui.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.naoljcson.africa.databinding.FragmentVideoPlayerBinding
import com.naoljcson.africa.utils.hide
import com.naoljcson.africa.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VideoPlayerFragment : Fragment() {
    private val viewModel: VideoViewModel by viewModels()
    private var _binding: FragmentVideoPlayerBinding? = null
    private val binding get() = _binding!!
    private lateinit var mediaController: MediaController
    private lateinit var id: String
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoPlayerBinding.inflate(layoutInflater, container, false)
        mediaController = MediaController(requireContext())
        id = arguments?.let { VideoPlayerFragmentArgs.fromBundle(it).id }.toString()
        progressBar = ProgressBar(requireContext())
        progressBar.show()
        with(binding) {
            videoView.apply {
                mediaController.setAnchorView(this)
                setOnPreparedListener { mp ->
                    mp?.setOnBufferingUpdateListener { _, i ->
                        if (i == 100) {
                            progressBar.hide()
                        }
                    }
                }
            }
        }
        viewModel.getVideo(id)
        observeVideo()
        return binding.root
    }

    private fun observeVideo() {
        lifecycleScope.launch {
            viewModel.ldVideo.collect { video ->
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