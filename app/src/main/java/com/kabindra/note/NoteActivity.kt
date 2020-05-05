package com.kabindra.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.kabindra.note.entity.Note
import com.kabindra.note.room.DatabaseService
import com.kabindra.note.utils.Utils
import kotlinx.android.synthetic.main.activity_note.*
import java.util.*


class NoteActivity : AppCompatActivity() {

    private lateinit var databaseService: DatabaseService
    private var noteId: Int = 0

    private var mYear = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    private var mHour: Int = 0
    private var mMinute: Int = 0

    private var mNoteContent: String? = null
    private var mNoteCreatedAt: Long? = null

    companion object {
        var NOTE_ID: String = "noteId"

        fun start(context: Context, noteId: Int?) {
            val intent = Intent(context, NoteActivity::class.java)
            /*intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK*/
            intent.putExtra(NOTE_ID, noteId)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        databaseService = DatabaseService(this)

        noteId = intent.getIntExtra(NOTE_ID, 0)

        if (noteId == 0) {
            title = "Add Note"

            // Initialize default values
            val mCalendar = Calendar.getInstance()
            mYear = mCalendar.get(Calendar.YEAR)
            mMonth = mCalendar.get(Calendar.MONTH) + 1
            mDay = mCalendar.get(Calendar.DATE)
            mHour = mCalendar.get(Calendar.HOUR_OF_DAY)
            mMinute = mCalendar.get(Calendar.MINUTE)

            mNoteContent = ""
            mNoteCreatedAt = mCalendar.timeInMillis
        } else {
            title = "Edit Note"

            // Initialize values
            val note = databaseService.getNoteByNoteId(noteId)

            mNoteContent = note?.noteContent
            mNoteCreatedAt = note?.noteCreatedAt
        }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        // Setup TextViews using note values
        note_content_set!!.setText(mNoteContent)
        note_content_created_at!!.text = Utils.getDateAndTime(mNoteCreatedAt)
        note_content_created_at.requestFocus()

        note_content_set.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                /*if (s.isNotEmpty())
                    saveNoteOnTextChange(s)*/
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_note, menu)

        val saveItem = menu?.findItem(R.id.action_save_note)
        val updateItem = menu?.findItem(R.id.action_update_note)
        val deleteItem = menu?.findItem(R.id.action_delete_note)

        if (noteId == 0) {
            saveItem!!.isVisible = true
            updateItem!!.isVisible = false
            deleteItem!!.isVisible = false
        } else {
            saveItem!!.isVisible = false
            updateItem!!.isVisible = true
            deleteItem!!.isVisible = true
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.action_save_note -> {
                saveNote()
                true
            }
            R.id.action_update_note -> {
                updateNote()
                true
            }
            R.id.action_delete_note -> {
                deleteNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveNote() {
        if (note_content_set.text!!.isEmpty())
            note_content_set.error = "Please make a note. Your note is empty."
        else {
            val note = databaseService.getLastNote()

            var noteID = note?.noteId ?: 0

            val noteId = noteID + 1

            val success = databaseService.insertNote(
                Note(
                    noteId,
                    note_content_set.text.toString(),
                    mNoteCreatedAt
                )
            )

            if (success == 1) {
                Utils.showSnackBar(
                    findViewById(android.R.id.content),
                    "Note inserted successfully."
                )
            }

            onBackPressed()
        }
    }

    private fun updateNote() {
        when {
            note_content_set.text!!.isEmpty() -> {
                note_content_set.error = "Please make a note. Your note is empty."
            }
            else -> {
                val success = databaseService.updateNote(
                    Note(
                        noteId,
                        note_content_set.text.toString(),
                        mNoteCreatedAt
                    )
                )

                if (success == 1) {
                    Utils.showSnackBar(
                        findViewById(android.R.id.content),
                        "Note updated successfully."
                    )
                }

                onBackPressed()
            }
        }
    }

    private fun deleteNote() {
        val note = databaseService.getNoteByNoteId(noteId)

        val success = databaseService.deleteNote(note!!)

        if (success == 1) {
            Utils.showSnackBar(
                findViewById(android.R.id.content),
                "Note deleted successfully."
            )
        }

        onBackPressed()
    }

}
