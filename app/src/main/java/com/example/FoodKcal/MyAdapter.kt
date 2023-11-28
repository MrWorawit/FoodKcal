package com.example.FoodKcal


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val items: Array<String>, private val imageId: Array<Int>, private val context : Context): RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.model, parent, false))
    }


    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.getNameText?.text = items[position]
        holder?.getThumbnail?.setImageResource(imageId[position])
        holder?.itemView?.setOnClickListener {
            //  Toast.makeText(context, "Click: "+ items[position], Toast.LENGTH_LONG).show()
            var intent = Intent(context, InformationActivity::class.java)

            intent.putExtra("TITLE",""+ items[position])
            intent.putExtra("IMAGE", imageId[position])


            context.startActivity(intent)



        }

    }


}
class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    // Holds the TextView that will add each animal to
    val getNameText: TextView? = itemView.findViewById(R.id.nameTxt)
    val getThumbnail: ImageView? = itemView.findViewById(R.id.thumbnail)


}