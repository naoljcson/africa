package com.naoljcson.africa.ui.gallery

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.naoljcson.africa.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    companion object {
        fun newInstance() = GalleryFragment()
    }

    private val viewModel: GalleryViewModel by viewModels()
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private val imagesUri = mutableListOf<Uri>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(layoutInflater, container, false)
        viewModel.getImagesUri()
        return binding.root
    }

    private fun observeImagesUri() {
        lifecycleScope.launchWhenStarted {
            viewModel.ldImages.collect { uriList ->
                if (uriList != null) {
                    imagesUri.addAll(uriList)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}