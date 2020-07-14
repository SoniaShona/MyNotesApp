package com.example.mynotesapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotesapp.R
import com.example.mynotesapp.db.Note
import kotlinx.android.synthetic.main.note_item.view.*

class NotesAdapter(private val notes : List<Note>) : RecyclerView.Adapter<NotesAdapter.NoteVH>() {
    class NoteVH(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteVH {
        return NoteVH (
            LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteVH, position: Int) {
        holder.view.titleTextView.text = notes[position].title
        holder.view.noteTextView.text = notes[position].note
        holder.view.dateTextView.text = notes[position].date

        holder.view.setOnClickListener{
            val action = HomeFragmentDirections.actionAddNote()
            action.note = notes[position]
            Navigation.findNavController(it).navigate(action)

        }
    }
}