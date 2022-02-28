package com.heske.example.flickerapp.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.heske.example.flickerapp.R
import com.heske.example.flickerapp.databinding.ActivityMainBinding
import com.heske.example.flickerapp.network.Photo
import com.heske.example.flickerapp.network.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()

    lateinit var binding: ActivityMainBinding
    lateinit var recyclerAdapter: PhotoRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        setupRecyclerView()
       // setupObservers()
    }

    private fun setupObservers() {
        viewModel.photos.observe(this) { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    //progress.visibility = GONE
                  //  binding.recyclerView.visibility = VISIBLE
                    response.data?.let { list ->
                        Log.d("RESPONSE","${list.size}")
                  //      recyclerAdapter.addAll(list)
                     //   setupRecyclerView(list)
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
        binding.photoRecyclerView.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = PhotoRecyclerAdapter(
            onItemClicked = { },
        )
        binding.photoRecyclerView.adapter = recyclerAdapter

        val list = listOf(
            Photo(),Photo(),Photo()
        )
        recyclerAdapter.addAll(list)

       // recyclerAdapter.addAll(photos)
    }
//    private fun setupRecyclerView() {
//        binding.recyclerView.setHasFixedSize(true)
//        binding.recyclerView.isNestedScrollingEnabled = false
//        recyclerAdapter = PhotoRecyclerAdapter(
//            onItemClicked = { photo ->
//            }
//        )
//        binding.recyclerView.adapter = recyclerAdapter
//        recyclerAdapter.addAll(listOf(
//            Photo(),Photo(),Photo()
//        ))
//    }
}