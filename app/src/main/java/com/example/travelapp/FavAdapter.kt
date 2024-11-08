package com.example.travelapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FavAdapter(
    private val context: Context,
    private val favItemList: List<FavItem>
) : RecyclerView.Adapter<FavAdapter.ViewHolder>() {

    private var travelDB: TravelDB = TravelDB(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fav_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favItem = favItemList[position]
        holder.favTextView.text = favItem.item_title
        holder.favImageView.setImageResource(favItem.item_image)

        holder.relative.setOnClickListener {
            val intent = Intent(context, Details::class.java).apply {
                putExtra("image", favItem.item_image)
                putExtra("titleTextView", favItem.item_title)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return favItemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val favTextView: TextView = itemView.findViewById(R.id.favTextView)
        val favBtn: Button = itemView.findViewById(R.id.favBtn)
        val favImageView: ImageView = itemView.findViewById(R.id.favImageView)
        val relative: RelativeLayout = itemView.findViewById(R.id.relative)

        init {
            favBtn.setOnClickListener {
                val position = adapterPosition
                val favItem = favItemList[position]
                travelDB.removeFav(favItem.key_id)
                removeItem(position)
            }
        }
    }

    private fun removeItem(position: Int) {
        (favItemList as MutableList).removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, favItemList.size)
    }
}
