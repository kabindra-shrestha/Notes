package com.kabindra.note.room

import com.kabindra.note.entity.Note

interface QueriesInterface {

    fun insertNote(vararg note: Note): Int

    fun updateNote(vararg note: Note): Int

    fun deleteNote(vararg note: Note): Int

    fun checkNote(noteId: Int): Boolean

    fun getAllNotes(): List<Note>?

    fun getNoteByNoteId(noteId: Int): Note?

    fun getLastNote(): Note?

}
