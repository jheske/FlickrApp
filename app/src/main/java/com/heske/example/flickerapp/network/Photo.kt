package com.heske.example.flickerapp.network

data class Photo(
    val author: String="",
    val author_id: String="",
    val date_taken: String="",
    val description: String="",
    val link: String="",
    val media: Media=Media(""),
    val published: String="",
    val tags: String="",
    val title: String="TITLE"
)