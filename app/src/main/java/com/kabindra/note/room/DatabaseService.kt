package com.kabindra.note.room

import android.content.Context
import com.kabindra.note.entity.Note

class DatabaseService(context: Context) : QueriesInterface {

    private val dao: RepoDao = AppDatabase.getInstance(context).repoDao()

    override fun insertNote(vararg note: Note): Int {
        return try {
            dao.insertNote(*note)
            1
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    override fun updateNote(vararg note: Note): Int {
        return try {
            dao.updateNote(*note)
            1
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    override fun deleteNote(vararg note: Note): Int {
        return try {
            dao.deleteNote(*note)
            1
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    override fun checkNote(noteId: Int): Boolean {
        return dao.checkNote(noteId)
    }

    override fun getAllNotes(): List<Note>? {
        return dao.getAllNotes()
    }

    override fun getNoteByNoteId(noteId: Int): Note? {
        return dao.getNoteByNoteId(noteId)
    }

    override fun getLastNote(): Note? {
        return dao.getLastNote()
    }

}