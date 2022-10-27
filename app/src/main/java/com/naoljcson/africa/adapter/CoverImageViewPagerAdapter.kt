package com.naoljcson.africa.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.naoljcson.africa.databinding.ItemCoverImageBinding
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

class CoverImageViewPagerAdapter(private val images: List<String>) :
    RecyclerView.Adapter<CoverImageViewPagerAdapter.ViewPagerViewHolder>() {

    private val firebaseStorage = FirebaseStorage.getInstance()
    private val storageReference: StorageReference = firebaseStorage.reference

    inner class ViewPagerViewHolder(val binding: ItemCoverImageBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val binding =
            ItemCoverImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerViewHolder(binding)
    }

    @SuppressLint("LongLogTag")
    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        with(holder) {
            with(images[position]) {
                storageReference.child(this)
                    .downloadUrl.addOnSuccessListener { uri ->
                        Picasso.get()
                            .load(uri)
                            .networkPolicy(NetworkPolicy.OFFLINE)
                            .into(binding.ivCover)
                    }.addOnFailureListener {
                        Log.d("CoverImageViewPagerAdapter", "Failed to load ${it.message}")
                    }
            }
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }
}