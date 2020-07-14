package com.example.mynotesapp.ui


import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mynotesapp.R
import com.example.mynotesapp.db.Note
import com.example.mynotesapp.db.NoteDataBase
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var notesList : List<Note>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerViewNotes.setHasFixedSize(true)
        recyclerViewNotes.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        getAllNotes()

        addNote.setOnClickListener {
            val action = HomeFragmentDirections.actionAddNote()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun getAllNotes() {

        class GetAllNotes : AsyncTask<Void, Void, Void>(){
            override fun doInBackground(vararg params: Void?): Void? {
                notesList = NoteDataBase(activity!!).noteDao().getAllNotes()
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                recyclerViewNotes.adapter = NotesAdapter(notesList)
            }
        }

        GetAllNotes().execute()


    }


}
