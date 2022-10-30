package com.naoljcson.africa.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naoljcson.africa.databinding.ItemGalleryImageBinding
import com.squareup.picasso.Picasso

class GalleryViewPagerAdapter(private val uriList: List<Uri>) :
    RecyclerView.Adapter<GalleryViewPagerAdapter.GalleryViewHolder>() {

    inner class GalleryViewHolder(val binding: ItemGalleryImageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        return GalleryViewHolder(
            ItemGalleryImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        Picasso
            .get()
            .load(uriList[position])
            .into(holder.binding.ivGallery)
    }

    override fun getItemCount(): Int {
        return uriList.size
    }
}