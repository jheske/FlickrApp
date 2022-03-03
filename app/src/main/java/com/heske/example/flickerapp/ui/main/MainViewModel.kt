package com.heske.example.flickerapp.ui.main

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heske.example.flickerapp.network.Photo
import com.heske.example.flickerapp.network.Resource
import com.heske.example.flickerapp.repository.Repository
import com.heske.example.flickerapp.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repository: Repository,
    val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _photos = MutableLiveData<Resource<List<Photo>>>()
    val photos: LiveData<Resource<List<Photo>>> = _photos

    private val _recentSearches = MutableLiveData<ArrayList<String>>()
    val recentSearches: LiveData<ArrayList<String>> = _recentSearches

//    init {
//        fetchPhotos("airedale")
//    }

    fun fetchPhotos(searchString: String) = viewModelScope.launch {
        _photos.postValue(Resource.loading(null))

        repository.fetchPhotos(searchString).let { response ->
            if (response.isSuccessful) {
                _photos.postValue(Resource.success(response.body()?.items))
            } else {
                _photos.postValue(Resource.error(response.errorBody().toString(), null))
            }
        }
    }

    fun getRecentSearches(): ArrayList<String> {
        return Utils.getRecentSearches(sharedPreferences)
    }

    fun saveRecentSearches(list: ArrayList<String>) {
        return Utils.saveRecentSearches(sharedPreferences,list)
    }
}