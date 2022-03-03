package com.heske.example.flickerapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import com.heske.example.flickerapp.R
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
    private lateinit var recentSearchesAdapter: ArrayAdapter<String>

    private var recentSearches = arrayListOf<String>()
    private var queryTextChangedJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        setupRecyclerView()
        setupObservers()
        setupRecentSearchesListView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val search = menu.findItem(R.id.searchBar)

        val searchView = search.actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val text = newText ?: return false
                searchText = text
                binding.recentSearchesListView.visibility = VISIBLE
                queryTextChangedJob?.cancel()
                queryTextChangedJob = lifecycleScope.launch(Dispatchers.Main) {
                    delay(2000)
                    Keyboard.dismiss(this@MainActivity)
                    updateRecentSearches(searchText)
                    viewModel.fetchPhotos(searchText)
                }
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
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
        recyclerAdapter = PhotoRecyclerAdapter {
            startActivity(DetailActivity.intent(this, it))
        }
        binding.photoRecyclerView.adapter = recyclerAdapter
    }

    private fun setupRecentSearchesListView() {
        recentSearches = viewModel.getRecentSearches()
        recentSearchesAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, recentSearches)
        binding.apply {
            recentSearchesListView.adapter = recentSearchesAdapter
            setSearchListVisibility()
            recentSearchesListView.onItemClickListener =
                OnItemClickListener { parent, view, position, id ->
                    viewModel.fetchPhotos(
                        recentSearchesListView.getItemAtPosition(position) as String
                    )
                }
        }
    }

    private fun setSearchListVisibility() {
        if (recentSearches.size > 0) {
            binding.recentSearchesListView.visibility = VISIBLE
            binding.recentSearchesTextView.text = getString(R.string.recent_searches)
        }
    }

    /**
     * Add save most recent search term.
     * Don't add to the list if searchTerm is an empty string,
     * or if it already exists in the list.
     */
    private fun updateRecentSearches(searchTerm: String) {
        if (searchTerm.isEmpty()
            || !recentSearches.firstOrNull { it == searchTerm }.isNullOrEmpty()
        ) {
            return
        }

        if (recentSearches.size >= 5) {
            recentSearches.removeAt(0)
        }
        recentSearches.add(searchTerm)
        viewModel.saveRecentSearches(recentSearches)
        setSearchListVisibility()
        recentSearchesAdapter.notifyDataSetChanged()
    }
}