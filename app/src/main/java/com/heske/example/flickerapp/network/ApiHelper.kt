package com.heske.example.flickerapp.network

import retrofit2.Response

interface ApiHelper {
    suspend fun getPhotos(): Response<FlickrResponse>
}