package com.naoljcson.africa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naoljcson.africa.databinding.ItemFactBinding

class FactsViewPagerAdapter(private val facts: List<String>) :
    RecyclerView.Adapter<FactsViewPagerAdapter.FactViewHolder>() {

    inner class FactViewHolder(val binding: ItemFactBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder {
        return FactViewHolder(
            ItemFactBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        holder.binding.tvFact.text = facts[position]
    }

    override fun getItemCount(): Int {
        return facts.size
    }
}