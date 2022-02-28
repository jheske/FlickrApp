package com.heske.example.flickerapp.repository

import com.heske.example.flickerapp.network.ApiHelper
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    private val apiHelper: ApiHelper
){
    suspend fun getPhotos() = apiHelper.getPhotos()
}