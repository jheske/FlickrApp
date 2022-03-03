package com.heske.example.flickerapp.util

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.heske.example.flickerapp.util.Constants.RECENT_SEARCHES_PREF
import org.jsoup.Jsoup
import javax.inject.Inject

class Utils @Inject constructor() {
    @Inject
    lateinit var sharedPrefs: SharedPreferences

    companion object {
        /**
         * Some Flickr images have longer descriptions, stored in the 3rd <p> element.
         * Others don't. In that case, use the shorter title from the 2nd <p> element.
         */
        fun getImageDescription(html: String): String {
            val parsedHtml = Jsoup.parse(html).select("p")
            // Return long-form text if there is any
            if (parsedHtml.size >= 3) {
                return parsedHtml.select("p")[2].text()
            }
            // Else return shorter text
            else return parsedHtml.select("p")[1].select("a").attr("title")
        }

        fun saveRecentSearches(sharedPrefs: SharedPreferences, list: ArrayList<String>) {
            sharedPrefs.edit().putString(RECENT_SEARCHES_PREF, Gson().toJson(list)).apply()
        }

        //getting the list from shared preference
        fun getRecentSearches(sharedPrefs: SharedPreferences): ArrayList<String> {
            val type = object : TypeToken<ArrayList<String>>() {}.type
            return Gson().fromJson(sharedPrefs.getString(RECENT_SEARCHES_PREF, null), type)
                ?: arrayListOf()
        }
    }
}