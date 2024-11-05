package com.example.travelapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.util.Calendar

@Suppress("DEPRECATION")
class NoteAdd : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var noteTitle: EditText
    private lateinit var noteDetails: EditText
    private lateinit var calendar: Calendar
    private lateinit var todaysDate: String
    private lateinit var currentTime: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_add)

        toolbar = findViewById(R.id.toolbar)
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Enter New Note"

        noteDetails = findViewById(R.id.note_Details)
        noteTitle = findViewById(R.id.note_Title)

        noteTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isNotEmpty()) {
                    supportActionBar?.title = s
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Current date & time
        calendar = Calendar.getInstance()
        todaysDate = "${calendar.get(Calendar.YEAR)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.DAY_OF_MONTH)}"
        Log.d("DATE", "Date: $todaysDate")
        currentTime = pad(calendar.get(Calendar.HOUR)) + ":" + pad(calendar.get(Calendar.MINUTE))
        Log.d("TIME", "Time: $currentTime")
    }

    private fun pad(time: Int): String {
        return if (time < 10) "0$time" else time.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.save_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save -> {
                if (noteTitle.text.length != 0) {
                    val note = Note(noteTitle.text.toString(), noteDetails.text.toString(), todaysDate, currentTime)
                    val sDB = SimpleDatabase(this)
                    val id = sDB.addNote(note)
                    val check = sDB.getNote(id)
                    Log.d("inserted a new note", "Note: $id -> Title: ${check?.title} Date: ${check?.date}")
                    onBackPressed()

                    Toast.makeText(this, "Note Saved.", Toast.LENGTH_SHORT).show()
                } else {
                    noteTitle.error = "Please enter Title."
                }
                true
            }
            R.id.delete -> {
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show()
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
