package com.example.travelapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class TravelDB(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_VERSION = 1
        private const val DATABASE_NAME = "TravelDB"
        private const val TABLE_NAME = "favoriteTable"
        const val KEY_ID = "id"
        const val ITEM_TITLE = "itemTitle"
        const val ITEM_IMAGE = "itemImage"
        const val FAVORITE_STATUS = "fStatus"

        private const val TABLE_NAME1 = "cityTable"
        private const val KEY_ID1 = "id"
        private const val ITEM_TITLE1 = "itemTitle1"
        private const val ITEM_IMAGE1 = "itemImage1"
        private const val POPULATION_TITLE = "populationTitle"
        private const val CITY_DESC = "cityDescription"
        private const val AIRPORT_TITLE = "airportTitle"
        private const val COUNTRY_NAME = "countryName"

        private const val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                "$KEY_ID TEXT, $ITEM_TITLE TEXT, $ITEM_IMAGE TEXT, $FAVORITE_STATUS TEXT)"

        private const val CREATE_TABLE1 = "CREATE TABLE $TABLE_NAME1 (" +
                "$KEY_ID1 TEXT, $ITEM_TITLE1 TEXT, $COUNTRY_NAME TEXT, " +
                "$ITEM_IMAGE1 TEXT, $POPULATION_TITLE TEXT, $CITY_DESC TEXT, $AIRPORT_TITLE TEXT)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
        db.execSQL(CREATE_TABLE1)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME1")
        onCreate(db)
    }

    // Create empty table
    fun insertEmpty() {
        val db = writableDatabase
        val cv = ContentValues()
        for (x in 1 until 60) {
            cv.put(KEY_ID, x)
            cv.put(FAVORITE_STATUS, "0")
            cv.put(KEY_ID1, x)

            db.insert(TABLE_NAME, null, cv)
            db.insert(TABLE_NAME1, null, cv)
        }
    }

    // Insert data into database
    fun insertIntoTheDatabase(itemTitle: String, itemImage: Int, id: String, favStatus: String) {
        val db = writableDatabase
        val cv = ContentValues().apply {
            put(ITEM_TITLE, itemTitle)
            put(ITEM_IMAGE, itemImage)
            put(KEY_ID, id)
            put(FAVORITE_STATUS, favStatus)
        }
        db.insert(TABLE_NAME, null, cv)
        Log.d("FavDB Status", "$itemTitle, favstatus - $favStatus - . $cv")
    }

    fun insertIntoTheDatabase(itemTitle1: String, countryTitle: String, itemImage1: Int, id: String, populationTitle: String, cityDesc: String, airportTitle: String) {
        val db = writableDatabase
        val cv = ContentValues().apply {
            put(ITEM_TITLE1, itemTitle1)
            put(COUNTRY_NAME, countryTitle)
            put(ITEM_IMAGE1, itemImage1)
            put(KEY_ID1, id)
            put(POPULATION_TITLE, populationTitle)
            put(CITY_DESC, cityDesc)
            put(AIRPORT_TITLE, airportTitle)
        }
        db.insert(TABLE_NAME1, null, cv)
        Log.d("TravelDB Status", "$itemTitle1, city - $populationTitle - . $cv")
    }

    // Read all favorite data
    public fun readAllData(id: String): Cursor {
        val db = readableDatabase
        val sql = "SELECT * FROM $TABLE_NAME WHERE $KEY_ID = $id"
        return db.rawQuery(sql, null)
    }

    // Read all city data
    fun readAllCityData(id: String): Cursor {
        val db = readableDatabase
        val sql = "SELECT * FROM $TABLE_NAME1 WHERE $KEY_ID1 = $id"
        return db.rawQuery(sql, null)
    }

    // Remove line from database
    fun removeFav(id: String) {
        val db = writableDatabase
        val sql = "UPDATE $TABLE_NAME SET $FAVORITE_STATUS = '0' WHERE $KEY_ID = $id"
        db.execSQL(sql)
        Log.d("remove", id)
    }

    // Select all favorite list
    fun selectAllFavoriteList(): Cursor {
        val db = readableDatabase
        val sql = "SELECT * FROM $TABLE_NAME WHERE $FAVORITE_STATUS = '1'"
        return db.rawQuery(sql, null)
    }
}
