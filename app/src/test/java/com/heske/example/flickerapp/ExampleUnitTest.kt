package com.heske.example.flickerapp

import com.heske.example.flickerapp.util.Utils
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    val MOCK_HTML = " <p><a href=\"https://www.flickr.com/people/keshet_rescue/\">Keshet Kennels &amp; Rescue</a> posted a photo:</p> <p><a href=\"https://www.flickr.com/photos/keshet_rescue/51900369960/\" title=\"So lovely to be outside enjoying all the fresh Airedale Terrier!\"><img src=\"https://live.staticflickr.com/65535/51900369960_bc269b9d6c_m.jpg\" width=\"192\" height=\"240\" alt=\"So lovely to be outside enjoying all the fresh Airedale Terrier!\" /></a></p>  Terrier"
    val MOCK_DESCRIPTION = "So lovely to be outside enjoying all the fresh Airedale Terrier!"

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun html_parse_test() {
        assertEquals(MOCK_DESCRIPTION, Utils.getImageDescription(MOCK_HTML))
    }
}