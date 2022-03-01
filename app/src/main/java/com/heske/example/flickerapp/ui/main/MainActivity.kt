package com.heske.example.flickerapp.ui.main

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import com.heske.example.flickerapp.databinding.ActivityMainBinding

import com.heske.example.flickerapp.network.Status
import com.heske.example.flickerapp.ui.detail.DetailActivity
import com.heske.example.flickerapp.util.Keyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerAdapter: PhotoRecyclerAdapter
    private lateinit var searchText: String

    var queryTextChangedJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        setupRecyclerView()
        setupObservers()
        setupSearchView()
    }

    private fun setupObservers() {
        viewModel.photos.observe(this) { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = GONE
                    binding.photoRecyclerView.visibility = VISIBLE
                    response.data?.let { list ->
                        Log.d("RESPONSE", "${list.size}")
                        recyclerAdapter.addAll(list)
                    }
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = VISIBLE
                    binding.photoRecyclerView.visibility = GONE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = GONE
                    binding.photoRecyclerView.visibility = VISIBLE
                }
            }
        }
    }

    private fun setupRecyclerView() {
        recyclerAdapter = PhotoRecyclerAdapter{
            startActivity(DetailActivity.intent(this,it))
        }
        binding.photoRecyclerView.adapter = recyclerAdapter
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val text = newText ?: return false
                searchText = text
                queryTextChangedJob?.cancel()
                queryTextChangedJob = lifecycleScope.launch(Dispatchers.Main) {
                    delay(2000)
                    Keyboard.dismiss(this@MainActivity)
                    viewModel.fetchPhotos(searchText)
                }
                return false
            }
        })
    }
}