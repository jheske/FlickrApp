package com.heske.example.flickerapp.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
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
): Parcelable