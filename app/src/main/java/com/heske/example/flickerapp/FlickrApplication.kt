package com.heske.example.flickerapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FlickrApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }

}