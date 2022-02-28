package com.heske.example.flickerapp.network

import retrofit2.Response
import retrofit2.http.GET

interface ApiService{
    //?format=json&nojsoncallback=1&tags=porcupine
    //@GET("?method=flickr.photos.getRecent&format=json&nojsoncallback=1")
    @GET("?format=json&nojsoncallback=1&tags=porcupine")
    suspend fun getPhotos(): Response<FlickrResponse>
}