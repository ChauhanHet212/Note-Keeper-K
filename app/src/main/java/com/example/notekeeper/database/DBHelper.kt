package com.example.notekeeper.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notekeeper.model.Note

@Database(entities = [Note::class], version = 1)
abstract class DBHelper: RoomDatabase() {
    abstract fun noteDAO(): NoteDAO

    companion object{
        const val DB_NAME = "NotesDB"
        const val TABLE_NAME = "Notes"
    }
}