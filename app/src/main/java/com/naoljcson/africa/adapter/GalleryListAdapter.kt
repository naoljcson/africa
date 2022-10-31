package com.naoljcson.africa.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naoljcson.africa.databinding.ItemImageBinding
import com.squareup.picasso.Picasso

class GalleryListAdapter(private val uriList: List<Uri>) :
    RecyclerView.Adapter<GalleryListAdapter.GalleryViewHolder>() {
    inner class GalleryViewHolder(val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        return GalleryViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        Log.i("this","naol73 onBindViewHolder")
        Picasso
            .get()
            .load(uriList[position])
            .into(holder.binding.ivGallery)
    }

    override fun getItemCount(): Int {
        return uriList.size
    }
}