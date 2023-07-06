package com.example.notekeeper.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notekeeper.model.Note

@Dao
interface NoteDAO {
    @Query("SELECT * FROM ${DBHelper.TABLE_NAME} ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert
    fun addNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)
}