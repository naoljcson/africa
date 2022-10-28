package com.naoljcson.africa.ui.home

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.StorageReference
import com.naoljcson.africa.adapter.AnimalsListAdapter
import com.naoljcson.africa.adapter.CoverImageViewPagerAdapter
import com.naoljcson.africa.data.model.Animal
import com.naoljcson.africa.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    companion object {
        private val TAG = HomeFragment::class.qualifiedName
    }

    @Inject
    lateinit var storageReference: StorageReference

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var coverImageViewPagerAdapter: CoverImageViewPagerAdapter
    private lateinit var animalsListAdapter: AnimalsListAdapter

//    private val images = mutableListOf<String>()
    private val animals = mutableListOf<Animal>()
    private val imagesUri = mutableListOf<Uri>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i("", "getCoverImage onCreateView")
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCoverImages()
        observerCoverImages()
        coverImageViewPagerAdapter = CoverImageViewPagerAdapter(imagesUri)
        binding.vpCoverImages.adapter = coverImageViewPagerAdapter

        animalsListAdapter = AnimalsListAdapter(animals)
        binding.rvAnimalList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvAnimalList.adapter = animalsListAdapter
        observeAnimals()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observerCoverImages() {

        lifecycleScope.launchWhenStarted {
            viewModel.ldImageUri.collect {
                if (it != null) {
                    imagesUri.addAll(it)
                }
                coverImageViewPagerAdapter.notifyDataSetChanged()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeAnimals() {
        viewModel.getAnimals()
        lifecycleScope.launchWhenStarted {
            viewModel.ldAnimals.collect {
                if (it != null) {
                    animals.addAll(it)
                    animalsListAdapter.notifyDataSetChanged()
                }
                Log.i("", "getCoverImage observeAnimals $it")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}