package com.kabindra.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kabindra.note.adapter.NoteRecyclerViewAdapter
import com.kabindra.note.room.DatabaseService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var databaseService: DatabaseService

    private lateinit var noteRecyclerViewAdapter: NoteRecyclerViewAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = "Note"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        databaseService = DatabaseService(this)

        showList(main_list, false)
        showError(main_error, false)

        linearLayoutManager = LinearLayoutManager(this)
        main_list.layoutManager = linearLayoutManager
        main_list.addItemDecoration(
            DividerItemDecoration(
                main_list.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.action_add_note -> {
                NoteActivity.start(this@MainActivity, null)
                true
            }
            R.id.action_about_note -> {
                AboutUsActivity.start(this@MainActivity, null)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()

        val notes = databaseService.getAllNotes()

        if (notes!!.isEmpty()) {
            showList(main_list, false)
            showError(main_error, true)
        } else {
            showList(main_list, true)
            showError(main_error, false)

            noteRecyclerViewAdapter = NoteRecyclerViewAdapter(this, notes)
            main_list.adapter = noteRecyclerViewAdapter

            noteRecyclerViewAdapter.setNotifyData(notes)

            noteRecyclerViewAdapter.setOnItemClickListener(object :
                NoteRecyclerViewAdapter.OnItemClickListener {
                override fun onClick(view: View, position: Int) {
                    NoteActivity.start(this@MainActivity, notes[position].noteId)
                }
            })
        }
    }

    /*private fun showSoftKeyboard(view: View, show: Boolean) {
        if (show) {
            if (view.requestFocus()) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            }
        } else {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun showProgress(progressBar: ProgressBar, show: Boolean) {
        if (show) {
            progressBar.visibility = View.VISIBLE
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            progressBar.visibility = View.GONE
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }*/

    private fun showList(main_list: RecyclerView, show: Boolean) {
        if (show) {
            main_list.visibility = View.VISIBLE
        } else {
            main_list.visibility = View.GONE
        }
    }

    private fun showError(main_error: TextView, show: Boolean) {
        if (show) {
            main_error.visibility = View.VISIBLE
        } else {
            main_error.visibility = View.GONE
        }
    }

}
