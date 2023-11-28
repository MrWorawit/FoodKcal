package com.example.FoodKcal

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class InformationActivity : AppCompatActivity() {
   // var textView: TextView? = null
    var imageView: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

       // textView = findViewById<TextView>(R.id.textView)
        imageView = findViewById<ImageView>(R.id.imageView)

        var intent = intent
        //textView!!.text = ""+intent.getStringExtra("TITLE")
        imageView!!.setImageResource(intent.getIntExtra("IMAGE",0))
    }
}
