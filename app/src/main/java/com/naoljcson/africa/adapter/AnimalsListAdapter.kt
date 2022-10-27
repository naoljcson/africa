package com.naoljcson.africa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naoljcson.africa.data.model.Animal
import com.naoljcson.africa.databinding.ItemAnimalBinding
import com.naoljcson.africa.databinding.ItemCoverImageBinding

class AnimalsListAdapter(private val animals: List<Animal>) :
    RecyclerView.Adapter<AnimalsListAdapter.AnimalViewHolder>() {

    inner class AnimalViewHolder(val binding: ItemAnimalBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val binding = ItemAnimalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        with(holder){
            animals[position].apply{
                binding.tvAnimalName.text = this.name
                binding.tvHeadline.text = this.headline
            }
        }
    }



    override fun getItemCount(): Int {
       return animals.size
    }
}