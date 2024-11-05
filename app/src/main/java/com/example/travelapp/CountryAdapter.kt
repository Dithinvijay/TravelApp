package com.example.travelapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CountryAdapter(private val countryItems: ArrayList<CountryItem>, private val context: Context) : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    private lateinit var travelDB: TravelDB

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        travelDB = TravelDB(context)

        // Create table first
        val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val firstStart = prefs.getBoolean("firstStart", true)
        if (firstStart) {
            createTableOnFirstStart()
        }

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val countryItem = countryItems[position]

        readCursorData(countryItem, holder)
        holder.imageView.setImageResource(countryItem.imageResourse)
        holder.titleTextView.text = countryItem.title
        holder.relative.setOnClickListener {
            val intent = Intent(context, Details::class.java)
            intent.putExtra("image", countryItem.imageResourse)
            intent.putExtra("titleTextView", countryItem.title)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return countryItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val favBtn: Button = itemView.findViewById(R.id.favBtn)
        val relative: RelativeLayout = itemView.findViewById(R.id.relative)

        init {
            // Add to favorites button
            favBtn.setOnClickListener {
                val position = adapterPosition
                val countryItem = countryItems[position]

                if (countryItem.favStatus == "0") {
                    countryItem.favStatus = "1"
                    countryItem.title?.let { it1 -> countryItem.keyId?.let { it2 ->
                        travelDB.insertIntoTheDatabase(it1, countryItem.imageResourse,
                            it2, countryItem.favStatus!!
                        )
                    } }
                    favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp)
                } else {
                    countryItem.favStatus = "0"
                    countryItem.keyId?.let { it1 -> travelDB.removeFav(it1) }
                    favBtn.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp)
                }
            }
        }
    }

    private fun createTableOnFirstStart() {
        travelDB.insertEmpty()

        val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean("firstStart", false)
        editor.apply()
    }

    @SuppressLint("Range")
    private fun readCursorData(countryItem: CountryItem, viewHolder: ViewHolder) {
        val cursor: Cursor? = countryItem.keyId?.let { travelDB.readAllData(it) }
        val db: SQLiteDatabase = travelDB.readableDatabase
        try {
            while (cursor?.moveToNext() == true) {
                val itemFavStatus = cursor.getString(cursor.getColumnIndex(TravelDB.FAVORITE_STATUS))
                countryItem.favStatus = itemFavStatus

                // Check favorite status
                if (itemFavStatus != null && itemFavStatus == "1") {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp)
                } else if (itemFavStatus != null && itemFavStatus == "0") {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp)
                }
            }
        } finally {
            cursor?.close()
            db.close()
        }
    }
}
