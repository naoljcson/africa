package com.naoljcson.africa.ui.animal.detail

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
import com.naoljcson.africa.R
import com.naoljcson.africa.adapter.CoverImageViewPagerAdapter
import com.naoljcson.africa.data.model.Animal
import com.naoljcson.africa.databinding.FragmentAnimalDetailBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AnimalDetailFragment : Fragment() {

    companion object {
        fun newInstance() = AnimalDetailFragment()
    }

    private val viewModel: AnimalDetailViewModel by viewModels()
    private var _binding: FragmentAnimalDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var id: String
    private lateinit var animal: Animal
    private  var imagesUri = mutableListOf<Uri>()
    private lateinit var adapter: CoverImageViewPagerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimalDetailBinding.inflate(layoutInflater, container, false)
        id = arguments?.let { AnimalDetailFragmentArgs.fromBundle(it).id }.toString()
        viewModel.getAnimal(id)
        adapter = CoverImageViewPagerAdapter(imagesUri)
        binding.vpGalleryImages.adapter = adapter
        observeAnimal()
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeAnimal() {
        lifecycleScope.launchWhenStarted {
            viewModel.ldAnimal.collect {
                if (it != null) {
                    animal = it
                    animal.galleryUri?.let { it1 -> imagesUri.addAll(it1) }
                    adapter.notifyDataSetChanged()
                    with(binding) {
                        Picasso
                            .get()
                            .load(animal.imageUri)
                            .into(ivHero)
                        tvAnimalName.text = animal.name
                        tvDescription.text = animal.description
                        tvHeadline.text = animal.headline
                        tvAllAboutAnimalName.text =
                            String.format(getString(R.string.all_about_animal_name), animal.name)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}