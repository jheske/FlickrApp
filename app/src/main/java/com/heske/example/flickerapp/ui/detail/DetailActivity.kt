package com.heske.example.flickerapp.ui.detail

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.heske.example.flickerapp.R
import com.heske.example.flickerapp.databinding.ActivityDetailBinding
import com.heske.example.flickerapp.network.Photo
import com.heske.example.flickerapp.util.Constants.PHOTO_EXTRA
import com.heske.example.flickerapp.util.Utils

class DetailActivity : AppCompatActivity() {
    companion object {
        fun intent(context: Context, photo: Photo) =
            Intent(context, DetailActivity::class.java).apply {
                putExtra(PHOTO_EXTRA, photo)
            }
    }

    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.image_details)
        setContentView(binding.root)
        intent.getParcelableExtra<Photo>(PHOTO_EXTRA)?.let {
            binding.photo = it
            setupContent(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupContent(photo: Photo) {
        Glide.with(this)
            .asBitmap()
            .load(photo.media.m)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                    val dimens = "${bitmap.width} x ${bitmap.height}"
                    binding.dimensTextView.text =
                        String.format(getString(R.string.image_size), dimens)
                    binding.imageView.setImageBitmap(bitmap)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
        binding.descriptionTextView.text = Utils.getImageDescription(photo.description)
        binding.imageView.contentDescription = photo.title
        binding.titleTextView.text = photo.title
        val photoBy = String.format(getString(R.string.photo_by), photo.author)
        binding.authorTextView.text = photoBy
    }
}
