package com.example.notekeeper

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.notekeeper.database.DBHelper
import com.example.notekeeper.model.Note

class NoteViewModel(application: Application): AndroidViewModel(application) {

    var db: DBHelper
    init {
        db = Room.databaseBuilder(application, DBHelper::class.java, DBHelper.DB_NAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    fun addNote(note: Note){
        db.noteDAO().addNote(note)
    }

    fun updateNote(note: Note){
        db.noteDAO().updateNote(note)
    }

    fun deleteNote(note: Note){
        db.noteDAO().deleteNote(note)
    }

    fun getAllNote(): LiveData<List<Note>> = db.noteDAO().getAllNotes()
}