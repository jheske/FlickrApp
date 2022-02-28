package com.heske.example.flickerapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.heske.example.flickerapp.R
import com.heske.example.flickerapp.network.Photo

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

//        // ArrayList of class ItemsViewModel
//        val data = ArrayList<TestItem>()
//
//        // This loop will create 20 Views containing
//        // the image with the count of view
//        for (i in 1..20) {
//            data.add(TestItem("Item " + i))
//        }

        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(arrayListOf(Photo(), Photo(), Photo()))

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
    }
}
