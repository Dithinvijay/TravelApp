package com.example.travelapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Details : AppCompatActivity(), View.OnClickListener {

    private lateinit var imageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var country: String
    private var imageResource: Int = 0
    private lateinit var addBtn: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        imageView = findViewById(R.id.image)
        titleTextView = findViewById(R.id.titleTextView)
        country = intent.getStringExtra("titleTextView") ?: ""
        imageResource = intent.getIntExtra("image", 0)

        titleTextView.text = country
        imageView.setImageResource(imageResource)

        addBtn = findViewById(R.id.notepadbutton)
        addBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val intent = Intent(this@Details, NoteActivity::class.java)
        startActivity(intent)
    }
}
