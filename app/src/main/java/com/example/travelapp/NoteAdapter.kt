package com.example.travelapp

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(private val context: Context, private val notes: List<Note>) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.custom_note_list_view, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull viewHolder: ViewHolder, position: Int) {
        val note = notes[position]
        val title = note.title
        val date = note.date
        val time = note.time
        val id = note.id
        Log.d("date on", "Date on: $date")

        viewHolder.nTitle.text = title
        viewHolder.nDate.text = date
        viewHolder.nTime.text = time
        viewHolder.nID.text = id.toString()
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nTitle: TextView = itemView.findViewById(R.id.nTitle)
        val nDate: TextView = itemView.findViewById(R.id.nDate)
        val nTime: TextView = itemView.findViewById(R.id.nTime)
        val nID: TextView = itemView.findViewById(R.id.listId)

        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, NoteDetail::class.java)
                intent.putExtra("ID", notes[adapterPosition].id)
                itemView.context.startActivity(intent)
            }
        }
    }
}
