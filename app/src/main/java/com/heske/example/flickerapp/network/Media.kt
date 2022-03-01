package com.heske.example.flickerapp.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Media(
    val m: String
): Parcelable