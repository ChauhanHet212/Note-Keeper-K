package com.example.notekeeper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Room
import com.example.notekeeper.adapter.NoteAdapter
import com.example.notekeeper.database.DBHelper
import com.example.notekeeper.databinding.ActivityMainBinding
import com.example.notekeeper.model.Note

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var db: DBHelper
    var notesList: ArrayList<Note> = arrayListOf()
    lateinit var adapter: NoteAdapter
    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addBtn.setOnClickListener(View.OnClickListener { view ->
            startActivity(Intent(this, AddActivity::class.java))
        })

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[NoteViewModel::class.java]

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.equals("")){
                    val arrayList: ArrayList<Note> = arrayListOf()
                    for (note in notesList){
                        if (note.title.toLowerCase().contains(newText.toString().toLowerCase())
                            or note.content.toLowerCase().contains(newText.toString().toLowerCase())){
                            arrayList.add(note)
                        }
                    }
                    adapter.filteredList(arrayList)
                } else {
                    adapter.filteredList(notesList)
                }
                return true
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllNote().observe(this, Observer { list ->
            notesList = list as ArrayList<Note>
            binding.recycler.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = NoteAdapter(this, list)
            binding.recycler.adapter = adapter

            if (list.isEmpty()){
                binding.animationView.visibility = View.VISIBLE
            } else {
                binding.animationView.visibility = View.GONE
            }
        })
    }
}