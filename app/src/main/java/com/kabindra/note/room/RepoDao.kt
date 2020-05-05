package com.kabindra.note.room

import androidx.room.*
import com.kabindra.note.entity.Note

@Dao
interface RepoDao {

    @Insert
    fun insertNote(vararg note: Note)

    @Update
    fun updateNote(vararg note: Note)

    @Delete
    fun deleteNote(vararg note: Note)

    @Query("Select * FROM note where noteId = :noteId")
    fun checkNote(noteId: Int): Boolean

    @Query("SELECT * FROM note")
    fun getAllNotes(): List<Note>

    @Query("SELECT * FROM note where noteId = :noteId")
    fun getNoteByNoteId(noteId: Int): Note

    @Query("SELECT * FROM note ORDER BY noteId DESC LIMIT 1")
    fun getLastNote(): Note

}