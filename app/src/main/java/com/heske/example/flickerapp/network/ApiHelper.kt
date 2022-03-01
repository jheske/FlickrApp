package com.heske.example.flickerapp.network

import retrofit2.Response

interface ApiHelper {
    suspend fun fetchPhotos(searchString: String): Response<FlickrResponse>
}