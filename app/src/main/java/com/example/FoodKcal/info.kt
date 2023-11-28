package com.example.FoodKcal


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.FoodKcal.R.*

class info : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    private var foods= arrayOf(
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10",
        "11",
        "12"
    )

    private var arrImg = arrayOf<Int>(
        drawable.a1a,
        drawable.a2a,
        drawable.a3a,
        drawable.a4a,
        drawable.a5a,
        drawable.a6a,
        drawable.a7a,
        drawable.a8a,
        drawable.a9a,
        drawable.a10a,
        drawable.a11a,
        drawable.a12a ,

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_info)

        recyclerView = findViewById<RecyclerView>(id.recyclerView)
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = GridLayoutManager(this, 2)
        val myAdapter = MyAdapter(foods,arrImg,this)
        recyclerView!!.adapter = myAdapter

    }

}