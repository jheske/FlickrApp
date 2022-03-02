package com.heske.example.flickerapp.util

import org.jsoup.Jsoup

class Utils {
    companion object {
        fun getImageDescription(html: String): String {
            var altText = ""
            val parsedHtml = Jsoup.parse(html).select("p")
            // Return long-form text if there is any
            if (parsedHtml.size >= 3) {
                return parsedHtml.select("p")[2].text()
            }
            // Else return shorter text
            else return parsedHtml.select("p")[1].select("a").attr("title")
        }
    }
}