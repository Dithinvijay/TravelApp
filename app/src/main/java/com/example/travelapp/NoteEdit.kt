package com.example.travelapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.util.Calendar

@Suppress("DEPRECATION")
class NoteEdit : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var nTitle: EditText
    private lateinit var nContent: EditText
    private lateinit var todaysDate: String
    private lateinit var currentTime: String
    private var nId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_edit)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val intent = intent
        nId = intent.getLongExtra("ID", 0)
        val db = SimpleDatabase(this)
        val note = db.getNote(nId)

        val title = note?.title
        val content = note!!.content
        nTitle = findViewById(R.id.note_Title)
        nContent = findViewById(R.id.note_Details)

        nTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                supportActionBar?.title = title
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isNotEmpty()) {
                    supportActionBar?.title = s
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        nTitle.setText(title)
        nContent.setText(content)

        // Set current date and time
        val calendar = Calendar.getInstance()
        todaysDate = "${calendar.get(Calendar.YEAR)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.DAY_OF_MONTH)}"
        Log.d("DATE", "Date: $todaysDate")
        currentTime = pad(calendar.get(Calendar.HOUR)) + ":" + pad(calendar.get(Calendar.MINUTE))
        Log.d("TIME", "Time: $currentTime")
    }

    private fun pad(time: Int): String {
        return if (time < 10) "0$time" else time.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.save_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save -> {
                val note = Note(nId, nTitle.text.toString(), nContent.text.toString(), todaysDate, currentTime)
                Log.d("EDITED", "edited: before saving id -> ${note.id}")
                val sDB = SimpleDatabase(applicationContext)
                val id = sDB.editNote(note)
                Log.d("EDITED", "EDIT: id $id")
                goToMain()
                Toast.makeText(this, "Edited Successfully.", Toast.LENGTH_SHORT).show()
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

    private fun goToMain() {
        val intent = Intent(this, NoteActivity::class.java)
        startActivity(intent)
    }
}
