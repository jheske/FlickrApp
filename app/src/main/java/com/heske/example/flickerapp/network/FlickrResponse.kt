package com.heske.example.flickerapp.network

data class FlickrResponse(
    val description: String,
    val generator: String,
    val items: List<Photo>,
    val link: String,
    val modified: String,
    val title: String
)