package com.example.mynotesapp.ui


import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation

import com.example.mynotesapp.R
import com.example.mynotesapp.db.Note
import com.example.mynotesapp.db.NoteDataBase
import kotlinx.android.synthetic.main.fragment_add_note.*
import java.text.SimpleDateFormat
import java.util.*


class AddNoteFragment : Fragment() {

    private var note : Note ? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            note = AddNoteFragmentArgs.fromBundle(it).note
            titleEditText.setText(note?.title)
            noteEditText.setText(note?.note)
        }


        saveNote.setOnClickListener{view ->
            val noteTitle = titleEditText.text.toString().trim()
            val noteBody = noteEditText.text.toString().trim()

            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())

            if (noteTitle.isEmpty()){
                titleEditText.error = "Veuillez introduire le titre"
                titleEditText.requestFocus()
                return@setOnClickListener
            }

            if (noteBody.isEmpty()){
                noteEditText.error = "Veuillez introduire le contenu de la note"
                noteEditText.requestFocus()
                return@setOnClickListener
            }

            if (note == null) {
                val newnote = Note(currentDate,noteTitle,noteBody)
                saveNote(newnote,view)
            }
            else {
                note?.date = currentDate
                note?.title = noteTitle
                note?.note = noteBody

                updateNote(note!!,view)
            }

        }

        deleteNote.setOnClickListener{view ->
            if (note == null) {
                Toast.makeText(activity,"Impossible de supprimer une note non encore enregistrée",Toast.LENGTH_LONG).show()
            }
            else {
                deleteNote(note!!,view)
            }

        }
    }

    private fun saveNote(note : Note,view :View) {
        class SaveNote : AsyncTask<Void,Void,Void>(){
            override fun doInBackground(vararg params: Void?): Void? {
                NoteDataBase(activity!!).noteDao().InsertNote(note)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)

                Toast.makeText(activity,"Note enregistrée",Toast.LENGTH_LONG).show()

                val action = AddNoteFragmentDirections.actionSaveNote()
                Navigation.findNavController(view).navigate(action)
            }
        }

        SaveNote().execute()

    }

    private fun updateNote(note : Note , view: View) {
        class UpdateNote : AsyncTask<Void,Void,Void>(){
            override fun doInBackground(vararg params: Void?): Void? {
                NoteDataBase(activity!!).noteDao().updateNote(note)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)

                Toast.makeText(activity,"Note modifiée",Toast.LENGTH_LONG).show()

                val action = AddNoteFragmentDirections.actionSaveNote()
                Navigation.findNavController(view).navigate(action)
            }
        }

        UpdateNote().execute()
    }

    private fun deleteNote(note:Note,view: View) {

        class DeleteNote : AsyncTask<Void,Void,Void>(){
            override fun doInBackground(vararg params: Void?): Void? {
                NoteDataBase(activity!!).noteDao().deleteNote(note)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)

                Toast.makeText(activity,"Note supprimée",Toast.LENGTH_LONG).show()

                val action = AddNoteFragmentDirections.actionSaveNote()
                Navigation.findNavController(view).navigate(action)
            }
        }

        DeleteNote().execute()

    }


}
