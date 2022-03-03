package com.heske.example.flickerapp.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService{
    @GET("?format=json&nojsoncallback=1")
    suspend fun fetchPhotos(
        @Query("tags") tags: String
    ): Response<FlickrResponse>
}