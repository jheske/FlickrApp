package com.heske.example.flickerapp.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.heske.example.flickerapp.databinding.ListitemPhotoBinding
import com.heske.example.flickerapp.network.Photo
import java.io.File

class PhotoRecyclerAdapter(
    private val onItemClicked: (Photo) -> Unit,
) : RecyclerView.Adapter<PhotoRecyclerAdapter.PhotosViewHolder>() {
    private var photos = arrayListOf<Photo>()

    class PhotosViewHolder(private val context: Context, viewBinding: ListitemPhotoBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        private var binding: ListitemPhotoBinding? = viewBinding

        fun bindItem(photo: Photo) {
            binding?.apply {
                this.photo = photo
                Glide.with(context)
                    .load(photo.media.m)
                    .into(imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        return PhotosViewHolder(
            parent.context,
            ListitemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        val photo = getItemAtPosition(position)
        holder.itemView.setOnClickListener {
            // Edit photo
            onItemClicked(photo)
        }
        holder.bindItem(photo)
    }

    override fun getItemCount() = photos.size

    private fun getItemAtPosition(position: Int): Photo {
        return photos[position]
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(list: List<Photo>) {
        photos = arrayListOf()
        photos.addAll(list)
        notifyDataSetChanged()
    }

    /**
     * Add an item to the end of the list
     */
    fun add(photo: Photo) {
        photos.add(photo)
    }
}


