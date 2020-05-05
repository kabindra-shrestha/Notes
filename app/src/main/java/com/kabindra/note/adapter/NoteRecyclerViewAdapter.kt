package com.kabindra.note.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kabindra.note.R
import com.kabindra.note.entity.Note
import com.kabindra.note.utils.Utils
import kotlinx.android.synthetic.main.adapter_note_recycler_view_items.view.*

class NoteRecyclerViewAdapter(val context: Context?, var notes: List<Note>?) :
    RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder>() {

    private lateinit var listener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.adapter_note_recycler_view_items, parent, false)
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item: Note = notes!!.get(position)

        viewHolder.note_content?.text = item.noteContent
        viewHolder.note_created_at?.text = Utils.getDateAndTime(item.noteCreatedAt)

        viewHolder.itemView.setOnClickListener { listener.onClick(it, position) }
    }

    override fun getItemCount(): Int {
        return notes!!.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val note_content = view.note_container
        val note_created_at = view.note_created_at
    }

    interface OnItemClickListener {
        fun onClick(view: View, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun setNotifyData(notes: List<Note>?) {
        this.notes = notes

        notifyDataSetChanged()
    }

}