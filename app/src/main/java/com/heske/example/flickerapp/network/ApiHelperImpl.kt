package com.heske.example.flickerapp.network

import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
):ApiHelper{
    override suspend fun getPhotos(): Response<FlickrResponse>  = apiService.getPhotos()
}