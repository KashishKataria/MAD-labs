package com.example.contentprovider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.provider.BaseColumns

class JokeContentProvider : ContentProvider() {

    private lateinit var dbHelper: JokeDbHelper

    companion object {
        const val AUTHORITY = "com.example.jokelist.provider"
        val BASE_CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY")

        const val PATH_JOKES = "jokes"

        private const val JOKE = 100
        private const val JOKE_ID = 101

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, PATH_JOKES, JOKE)
            uriMatcher.addURI(AUTHORITY, "$PATH_JOKES/#", JOKE_ID)
        }
    }

    override fun onCreate(): Boolean {
        dbHelper = JokeDbHelper(context!!)
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = dbHelper.writableDatabase
        val id = db.insert(JokeContract.JokeEntry.TABLE_NAME, null, values)
        if (id > 0) {
            context?.contentResolver?.notifyChange(uri, null)
            return ContentUris.withAppendedId(uri, id)
        }
        throw SQLException("Failed to insert row into $uri")
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val db = dbHelper.readableDatabase
        val match = uriMatcher.match(uri)
        return when (match) {
            JOKE -> db.query(
                JokeContract.JokeEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
            )
            JOKE_ID -> {
                val newSelection = "${BaseColumns._ID}=?"
                val newSelectionArgs = arrayOf(ContentUris.parseId(uri).toString())
                db.query(
                    JokeContract.JokeEntry.TABLE_NAME,
                    projection,
                    newSelection,
                    newSelectionArgs,
                    null,
                    null,
                    sortOrder
                )
            }
            else -> throw IllegalArgumentException("Cannot query unknown URI $uri")
        }
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val db = dbHelper.writableDatabase
        val match = uriMatcher.match(uri)
        return when (match) {
            JOKE -> {
                db.update(JokeContract.JokeEntry.TABLE_NAME, values, selection, selectionArgs)
            }
            JOKE_ID -> {
                val newSelection = "${BaseColumns._ID}=?"
                val newSelectionArgs = arrayOf(ContentUris.parseId(uri).toString())
                db.update(JokeContract.JokeEntry.TABLE_NAME, values, newSelection, newSelectionArgs)
            }
            else -> throw IllegalArgumentException("Update is not supported for $uri")
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = dbHelper.writableDatabase
        val match = uriMatcher.match(uri)
        return when (match) {
            JOKE -> {
                db.delete(JokeContract.JokeEntry.TABLE_NAME, selection, selectionArgs)
            }
            JOKE_ID -> {
                val newSelection = "${BaseColumns._ID}=?"
                val newSelectionArgs = arrayOf(ContentUris.parseId(uri).toString())
                db.delete(JokeContract.JokeEntry.TABLE_NAME, newSelection, newSelectionArgs)
            }
            else -> throw IllegalArgumentException("Delete is not supported for $uri")
        }
    }

    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            JOKE -> "vnd.android.cursor.dir/$AUTHORITY.$PATH_JOKES"
            JOKE_ID -> "vnd.android.cursor.item/$AUTHORITY.$PATH_JOKES"
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
    }

    class JokeDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        companion object {
            const val DATABASE_NAME = "jokes.db"
            const val DATABASE_VERSION = 1
        }

        override fun onCreate(db: SQLiteDatabase) {
            val SQL_CREATE_JOKES_TABLE = "CREATE TABLE ${JokeContract.JokeEntry.TABLE_NAME} (" +
                    "${JokeContract.JokeEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${JokeContract.JokeEntry.COLUMN_JOKE} TEXT NOT NULL)"
            db.execSQL(SQL_CREATE_JOKES_TABLE)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS ${JokeContract.JokeEntry.TABLE_NAME}")
            onCreate(db)
        }
    }
}

object JokeContract {
    object JokeEntry : BaseColumns {
        const val TABLE_NAME = "jokes"
        const val COLUMN_ID = "_id"
        const val COLUMN_JOKE = "joke"
    }
}