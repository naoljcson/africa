package com.naoljcson.africa.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naoljcson.africa.databinding.ItemCoverImageBinding
import com.squareup.picasso.Picasso

class CoverImageViewPagerAdapter(private val images: List<Uri>) :
    RecyclerView.Adapter<CoverImageViewPagerAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(val binding: ItemCoverImageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val binding =
            ItemCoverImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        with(holder) {
            with(images[position]) {
                Picasso.get()
                    .load(this)
                    .into(binding.ivCover)
            }
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }
}