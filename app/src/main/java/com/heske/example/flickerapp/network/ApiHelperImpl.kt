package com.heske.example.flickerapp.network

import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {
    override suspend fun fetchPhotos(searchString: String): Response<FlickrResponse> =
        apiService.fetchPhotos(searchString)
}