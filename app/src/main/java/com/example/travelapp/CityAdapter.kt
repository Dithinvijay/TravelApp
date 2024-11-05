package com.example.travelapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CityAdapter(
    private val cityItems: ArrayList<CityItem>,
    private val context: Context
) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    private lateinit var travelDB: TravelDB

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        travelDB = TravelDB(context)

        val prefs: SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val firstStart: Boolean = prefs.getBoolean("firstStart", true)
        if (firstStart) {
            createTableOnFirstStart()
        }

        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_cityitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cityItem = cityItems[position]
        readCursorData(cityItem)

        holder.imageView1.setImageResource(cityItem.imageResourse1)
        holder.title1TextView.text = cityItem.title1
        holder.countryNameTextView.text = cityItem.countryname
        holder.populationTextView.text = cityItem.population
        holder.citydescTextView.text = cityItem.citydesc
        holder.airportTextView.text = cityItem.airport

        holder.relative.setOnClickListener {
            val intent = Intent(context, CityDetails::class.java).apply {
                putExtra("image1", cityItem.imageResourse1)
                putExtra("title1TextView", cityItem.title1)
                putExtra("countryNameTextView", cityItem.countryname)
                putExtra("populationTextView", cityItem.population)
                putExtra("citydescTextView", cityItem.citydesc)
                putExtra("airportTextView", cityItem.airport)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return cityItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView1: ImageView = itemView.findViewById(R.id.imageView1)
        val title1TextView: TextView = itemView.findViewById(R.id.title1TextView)
        val countryNameTextView: TextView = itemView.findViewById(R.id.countryNameTextView)
        val populationTextView: TextView = itemView.findViewById(R.id.populationTextView)
        val citydescTextView: TextView = itemView.findViewById(R.id.citydescTextView)
        val airportTextView: TextView = itemView.findViewById(R.id.airportTextView)
        val relative: RelativeLayout = itemView.findViewById(R.id.relative)
    }

    private fun createTableOnFirstStart() {
        travelDB.insertEmpty()

        val prefs: SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        with(prefs.edit()) {
            putBoolean("firstStart", false)
            apply()
        }
    }

    private fun readCursorData(cityItem: CityItem) {
        val cursor: Cursor = travelDB.readAllCityData(cityItem.key_id1)
        val db: SQLiteDatabase = travelDB.readableDatabase
        cursor.close()
        db.close()
    }
}
