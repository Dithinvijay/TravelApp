package com.example.travelapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class SimpleDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "SimpleDB"
        private const val TABLE_NAME = "SimpleTable"

        private const val KEY_ID = "id"
        private const val KEY_TITLE = "title"
        private const val KEY_CONTENT = "content"
        private const val KEY_DATE = "date"
        private const val KEY_TIME = "time"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createDb = ("CREATE TABLE $TABLE_NAME ("
                + "$KEY_ID INTEGER PRIMARY KEY,"
                + "$KEY_TITLE TEXT,"
                + "$KEY_CONTENT TEXT,"
                + "$KEY_DATE TEXT,"
                + "$KEY_TIME TEXT)")
        db.execSQL(createDb)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion >= newVersion) return

        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addNote(note: Note): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_TITLE, note.title)
            put(KEY_CONTENT, note.content)
            put(KEY_DATE, note.date)
            put(KEY_TIME, note.time)
        }

        return db.insert(TABLE_NAME, null, values)
    }

    fun getNote(id: Long): Note? {
        val db = this.writableDatabase
        val query = arrayOf(KEY_ID, KEY_TITLE, KEY_CONTENT, KEY_DATE, KEY_TIME)
        val cursor: Cursor? = db.query(
            TABLE_NAME, query, "$KEY_ID=?", arrayOf(id.toString()), null, null, null
        )

        return cursor?.let {
            if (it.moveToFirst()) {
                Note(
                    it.getLong(0),
                    it.getString(1),
                    it.getString(2),
                    it.getString(3),
                    it.getString(4)
                )
            } else {
                null
            }
        }
    }

    fun getAllNotes(): List<Note> {
        val allNotes = mutableListOf<Note>()
        val query = "SELECT * FROM $TABLE_NAME ORDER BY $KEY_ID DESC"
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val note = Note().apply {
                    id = cursor.getLong(0)
                    title = cursor.getString(1)
                    content = cursor.getString(2)
                    date = cursor.getString(3)
                    time = cursor.getString(4)
                }
                allNotes.add(note)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return allNotes
    }

    fun editNote(note: Note): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_TITLE, note.title)
            put(KEY_CONTENT, note.content)
            put(KEY_DATE, note.date)
            put(KEY_TIME, note.time)
        }
        Log.d("Edited", "Edited Title: -> ${note.title} \n ID -> ${note.id}")
        return db.update(TABLE_NAME, values, "$KEY_ID=?", arrayOf(note.id.toString()))
    }

    fun deleteNote(id: Long) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$KEY_ID=?", arrayOf(id.toString()))
        db.close()
    }
}
