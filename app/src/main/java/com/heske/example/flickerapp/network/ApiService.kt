package com.heske.example.flickerapp.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService{
    //?format=json&nojsoncallback=1&tags=porcupine
    //@GET("?method=flickr.photos.getRecent&format=json&nojsoncallback=1")
    @GET("?format=json&nojsoncallback=1")
    suspend fun fetchPhotos(
        @Query("tags") tags: String
    ): Response<FlickrResponse>
}