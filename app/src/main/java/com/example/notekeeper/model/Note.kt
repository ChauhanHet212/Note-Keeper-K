package com.example.notekeeper.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notekeeper.database.DBHelper

@Entity(tableName = DBHelper.TABLE_NAME)
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "Title") val title: String,
    @ColumnInfo(name = "Content") val content: String,
    @ColumnInfo(name = "time") val time: String
): java.io.Serializable
