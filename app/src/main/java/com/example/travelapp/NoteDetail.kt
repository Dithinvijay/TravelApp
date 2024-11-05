package com.example.travelapp

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

@Suppress("NAME_SHADOWING", "DEPRECATION")
class NoteDetail : AppCompatActivity() {
    private var id: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val intent = intent
        id = intent.getLongExtra("ID", 0)
        val db = SimpleDatabase(this)
        val note = db.getNote(id)

        supportActionBar?.title = note?.title
        val details = findViewById<TextView>(R.id.noteDesc)
        details.text = note?.content
        details.movementMethod = ScrollingMovementMethod()

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            val db = SimpleDatabase(applicationContext)
            db.deleteNote(id)
            Toast.makeText(applicationContext, "Note Deleted", Toast.LENGTH_SHORT).show()
            goToMain()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.edit_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit -> {
                val intent = Intent(this, NoteEdit::class.java)
                intent.putExtra("ID", id)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun goToMain() {
        val intent = Intent(this, NoteActivity::class.java)
        startActivity(intent)
    }
}
