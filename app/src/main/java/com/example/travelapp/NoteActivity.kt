package com.example.travelapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@Suppress("DEPRECATION")
class NoteActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var noItemText: TextView
    private lateinit var simpleDatabase: SimpleDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        toolbar = findViewById(R.id.toolbar)
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "My Notes"
        }

        noItemText = findViewById(R.id.noItemText)
        simpleDatabase = SimpleDatabase(this)

        val allNotes = simpleDatabase.getAllNotes()
        recyclerView = findViewById(R.id.allNotesList)

        if (allNotes.isEmpty()) {
            noItemText.visibility = View.VISIBLE
        } else {
            noItemText.visibility = View.GONE
            displayList(allNotes)
        }
    }

    private fun displayList(allNotes: List<Note>) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        noteAdapter = NoteAdapter(this, allNotes)
        recyclerView.adapter = noteAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.note_main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add -> {
                Toast.makeText(this, "Add New Note", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, NoteAdd::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        val getAllNotes = simpleDatabase.getAllNotes()
        if (getAllNotes.isEmpty()) {
            noItemText.visibility = View.VISIBLE
        } else {
            noItemText.visibility = View.GONE
            displayList(getAllNotes)
        }
    }
}
