package com.naoljcson.africa.ui.home

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.storage.StorageReference
import com.naoljcson.africa.R
import com.naoljcson.africa.adapter.AnimalsListAdapter
import com.naoljcson.africa.adapter.CoverImageViewPagerAdapter
import com.naoljcson.africa.data.model.Animal
import com.naoljcson.africa.databinding.FragmentHomeBinding
import com.naoljcson.africa.utils.OnClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), OnClickListener {

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
    private val animals = mutableListOf<Animal>()
    private val imagesUri = mutableListOf<Uri>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCoverImages()
        viewModel.getAnimals()
        observerCoverImages()
        observeAnimals()
        animalsListAdapter = AnimalsListAdapter(animals,this)
        coverImageViewPagerAdapter = CoverImageViewPagerAdapter(imagesUri)

        with(binding){
            with(vpCoverImages){
                adapter = coverImageViewPagerAdapter
                indicator.attachToPager(this)
            }
            with(rvAnimalList){
                layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false)
                adapter = animalsListAdapter
            }
            shimmerCoverContainer.startShimmer()
            shimmerAnimalContainer.startShimmer()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observerCoverImages() {
        lifecycleScope.launchWhenStarted {
            viewModel.ldImageUri.collect { URIs ->
                if (URIs != null) {
                    imagesUri.addAll(URIs)
                    with(binding) {
                        with(shimmerCoverContainer) {
                            stopShimmer()
                            visibility = View.GONE
                        }
                        vpCoverImages.visibility = View.VISIBLE
                        indicator.visibleDotCount = URIs.size
                    }
                    coverImageViewPagerAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeAnimals() {
        lifecycleScope.launchWhenStarted {
            viewModel.ldAnimals.collect {
                if (it != null) {
                    animals.addAll(it)
                    with(binding) {
                        rvAnimalList.visibility = View.VISIBLE
                        with(shimmerAnimalContainer) {
                            stopShimmer()
                            visibility = View.GONE
                        }
                    }
                    animalsListAdapter.notifyDataSetChanged()
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
        bundle.putString("id", animals[position].id)
        findNavController().navigate(R.id.action_nav_home_dest_to_animalDetailFragment, bundle)
    }
}