package com.naoljcson.africa.ui.animal.detail

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.naoljcson.africa.R
import com.naoljcson.africa.adapter.FactsViewPagerAdapter
import com.naoljcson.africa.adapter.GalleryViewPagerAdapter
import com.naoljcson.africa.data.model.Animal
import com.naoljcson.africa.databinding.FragmentAnimalDetailBinding
import com.naoljcson.africa.utils.hide
import com.naoljcson.africa.utils.show
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimalDetailFragment : Fragment() {

    companion object {
        fun newInstance() = AnimalDetailFragment()
    }

    private val viewModel: AnimalDetailViewModel by viewModels()
    private var _binding: FragmentAnimalDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var id: String

    //    private lateinit var animal: Animal
    private var imagesUri = mutableListOf<Uri>()
    private var facts = mutableListOf<String>()
    private lateinit var galleryViewPagerAdapter: GalleryViewPagerAdapter
    private lateinit var factsPagerAdapter: FactsViewPagerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimalDetailBinding.inflate(layoutInflater, container, false)
        id = arguments?.let { AnimalDetailFragmentArgs.fromBundle(it).id }.toString()
        viewModel.getAnimal(id)
        galleryViewPagerAdapter = GalleryViewPagerAdapter(imagesUri)
        factsPagerAdapter = FactsViewPagerAdapter(facts)
        with(binding) {
            with(vpGalleryImages) {
                adapter = galleryViewPagerAdapter
                clipToPadding = false
                clipChildren = false
                offscreenPageLimit = 3
            }
            vpFacts.adapter = factsPagerAdapter
        }
        observeAnimal()
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeAnimal() {
        lifecycleScope.launchWhenStarted {
            viewModel.ldAnimal.collect {
                it?.let { animal ->
                    animal.galleryUri?.let { it1 -> imagesUri.addAll(it1) }
                    animal.fact?.let { it1 -> facts.addAll(it1) }
                    factsPagerAdapter.notifyDataSetChanged()
                    galleryViewPagerAdapter.notifyDataSetChanged()
                    with(binding) {
                        with(shimmerAnimalNameContainer) {
                            stopShimmer()
                            hide()
                        }
                        with(shimmerHeroContainer) {
                            hide()
                            stopShimmer()
                        }
                        with(shimmerHeadLineContainer) {
                            hide()
                            stopShimmer()
                        }
                        with(shimmerGalleryContainer) {
                            hide()
                            stopShimmer()
                        }
                        with(ivHero) {
                            show()
                            Picasso
                                .get()
                                .load(animal.imageUri)
                                .into(this)
                        }
                        with(tvAnimalName) {
                            show()
                            text = animal.name
                        }
                        with(tvDescription) {
                            show()
                            text = animal.description
                        }
                        with(tvHeadline) {
                            show()
                            text = animal.headline
                        }
                        vpGalleryImages.visibility = View.VISIBLE

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