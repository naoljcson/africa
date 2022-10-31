package com.naoljcson.africa.ui.gallery

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.naoljcson.africa.adapter.GalleryListAdapter
import com.naoljcson.africa.databinding.FragmentGalleryBinding
import com.naoljcson.africa.utils.hide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    companion object {
        fun newInstance() = GalleryFragment()
    }

    private val viewModel: GalleryViewModel by viewModels()
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private val imagesUri = mutableListOf<Uri>()
    private lateinit var galleryListAdapter: GalleryListAdapter
    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        galleryListAdapter = GalleryListAdapter(imagesUri)
        gridLayoutManager = GridLayoutManager(requireContext(), 2)
        viewModel.getImagesUri()
        observeImagesUri()
        binding.shimmerGalleryContainer.startShimmer()
        with(binding.rvGallery) {
            adapter = galleryListAdapter
            layoutManager = gridLayoutManager

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeImagesUri() {
        lifecycleScope.launchWhenStarted {
            viewModel.ldImages.collect { uriList ->
                if (uriList != null) {
                    imagesUri.addAll(uriList)
                    binding.rvGallery.visibility = View.VISIBLE
                    galleryListAdapter.notifyDataSetChanged()
                    binding.shimmerGalleryContainer.apply {
                        hide()
                        stopShimmer()
                    }
                }
            }
        }
    }

    private fun setSpanCount(numberOfSpan: Int) {
        gridLayoutManager.spanCount = numberOfSpan
    }
//
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}