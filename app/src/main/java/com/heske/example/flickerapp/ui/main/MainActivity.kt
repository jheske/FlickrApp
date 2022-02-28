package com.heske.example.flickerapp.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.heske.example.flickerapp.databinding.ActivityMainBinding
import com.heske.example.flickerapp.network.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()

    lateinit var binding: ActivityMainBinding
    lateinit var recyclerAdapter: PhotoRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        setupRecyclerView()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.photos.observe(this) { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    //progress.visibility = GONE
                    //  binding.recyclerView.visibility = VISIBLE
                    response.data?.let { list ->
                        Log.d("RESPONSE", "${list.size}")
                        recyclerAdapter.addAll(list)
                    }
                }
                Status.LOADING -> {
                    // progress.visibility = VISIBLE
                    //   binding.recyclerView.visibility = GONE
                }
                Status.ERROR -> {
                    // progress.visibility = GONE
                    //  binding.recyclerView.visibility = VISIBLE
                }
            }
        }
    }

    private fun setupRecyclerView() {
        recyclerAdapter = PhotoRecyclerAdapter{}
        binding.photoRecyclerView.adapter = recyclerAdapter
    }
}