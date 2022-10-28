package com.naoljcson.africa.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.naoljcson.africa.data.model.Animal
import com.naoljcson.africa.databinding.ItemAnimalBinding
import com.naoljcson.africa.databinding.ItemCoverImageBinding
import com.naoljcson.africa.utils.toJPG
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import javax.inject.Inject

class AnimalsListAdapter(private val animals: List<Animal>) :
    RecyclerView.Adapter<AnimalsListAdapter.AnimalViewHolder>() {

    private val firebaseStorage = FirebaseStorage.getInstance()
    private val storageReference: StorageReference = firebaseStorage.reference


    inner class AnimalViewHolder(val binding: ItemAnimalBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        val DiffUtil = object : DiffUtil.ItemCallback<Animal>() {
            override fun areItemsTheSame(oldItem: Animal, newItem: Animal) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Animal, newItem: Animal) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val binding = ItemAnimalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        with(holder) {
            animals[position].apply {
                binding.tvAnimalName.text = this.name
                binding.tvHeadline.text = this.headline
                this.image?.let { image ->
                    Log.i("naol","naol image ${image.toJPG()}")
                    storageReference
                        .child(image.toJPG())
                        .downloadUrl
                        .addOnSuccessListener { uri ->
                            Log.i("naol","naol uri $uri")
                            Picasso
                                .get()
                                .load(uri)
                                .into(binding.imageView)
                        }
                }
            }
        }
    }


    override fun getItemCount(): Int {
        return animals.size
    }
}