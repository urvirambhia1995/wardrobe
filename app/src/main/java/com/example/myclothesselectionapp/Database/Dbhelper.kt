package com.example.myclothesselectionapp.Database


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val wardrobelist = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                Top_COl + "TEXT," +
                Bottom_COL + " TEXT" + ")")
        val wardrobefavoritelist = ("CREATE TABLE " + TABLE_NAME_fav + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                Top_COl + "TEXT," +
                Bottom_COL + " TEXT" + ")")

        db.execSQL(wardrobelist)
        db.execSQL(wardrobefavoritelist)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_fav)

        onCreate(db)
    }

    fun add(top : String, bottom : String ){

        val values = ContentValues()

        values.put(Top_COl, top)
        values.put(Bottom_COL, bottom)

        val db = this.writableDatabase

        db.insert(TABLE_NAME, null, values)

        db.close()
    }
    fun addfav(top : String, bottom : String ){

        val values = ContentValues()

        values.put(Top_COl, top)
        values.put(Bottom_COL, bottom)

        val db = this.writableDatabase

        db.insert(TABLE_NAME_fav, null, values)

        db.close()
    }


    fun get(): Cursor? {

        val db = this.readableDatabase

        return db.rawQuery("SELECT Top,Bottom FROM " + TABLE_NAME, null)

    }
    fun getFav(): Cursor? {

        val db = this.readableDatabase

        return db.rawQuery("SELECT Top,Bottom FROM " + TABLE_NAME, null)

    }

    companion object{
        private val DATABASE_NAME = "wardrobe"

        private val DATABASE_VERSION = 1

        val TABLE_NAME = "wardrobe_table"
        val TABLE_NAME_fav = "favorite_table"


        val ID_COL = "id"

        val Top_COl = "Top"

        val Bottom_COL = "Bottom"
    }
}
