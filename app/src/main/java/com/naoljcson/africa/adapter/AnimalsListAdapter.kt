package com.naoljcson.africa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naoljcson.africa.data.model.Animal
import com.naoljcson.africa.databinding.ItemAnimalBinding
import com.naoljcson.africa.utils.OnClickListener
import com.squareup.picasso.Picasso

class AnimalsListAdapter(private val animals: List<Animal>, private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<AnimalsListAdapter.AnimalViewHolder>() {

    inner class AnimalViewHolder(val binding: ItemAnimalBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val binding = ItemAnimalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        with(holder) {
            animals[position].apply {
                binding.tvAnimalName.text = this.name
                binding.tvHeadline.text = this.headline
                this.imageUri?.let { uri ->
                    Picasso
                        .get()
                        .load(uri)
                        .into(binding.imageView)
                }
            }
            binding.root.setOnClickListener {
                onClickListener.onClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return animals.size
    }


}