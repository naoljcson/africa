package com.naoljcson.africa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naoljcson.africa.data.model.Animal
import com.naoljcson.africa.data.model.Video
import com.naoljcson.africa.databinding.ItemVideoBinding
import com.naoljcson.africa.utils.OnClickListener
import com.squareup.picasso.Picasso

class VideoListAdapter(
    private val videoList: List<Video>,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<VideoListAdapter.VideoViewHolder>() {
    inner class VideoViewHolder(val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(
            ItemVideoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        with(holder) {
            with(videoList[position]) {
                binding.tvAnimalName.text = this.name
                binding.tvHeadline.text = this.headline
                binding.root.setOnClickListener {
                    onClickListener.onClick(position)
                }
                if (this.imageUri != null) {
                    Picasso
                        .get()
                        .load(this.imageUri)
                        .into(binding.imageView)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

}