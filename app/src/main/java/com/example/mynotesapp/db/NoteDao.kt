package com.example.mynotesapp.db

import androidx.room.*


@Dao
interface NoteDao  {
    @Insert
    fun InsertNote(note:Note)


    @Query("SELECT * FROM note ORDER BY date DESC")
    fun getAllNotes() : List<Note>

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note:Note)
}