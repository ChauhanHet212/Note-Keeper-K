package com.example.notekeeper

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.notekeeper.database.DBHelper
import com.example.notekeeper.databinding.ActivityAddBinding
import com.example.notekeeper.model.Note
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Date

class AddActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddBinding
    lateinit var db: DBHelper
    var i = 0
    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener(View.OnClickListener { view ->
            finish()
        })


        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[NoteViewModel::class.java]

        i = intent.getIntExtra("key", 0)
        val note: Note? = intent.getSerializableExtra("data") as Note?
        if (i == 1){
            binding.deleteBtn.visibility = View.VISIBLE
            binding.title.text = "Edit Note"
            binding.titleEdt.setText(note?.title)
            binding.noteEdt.setText(note?.content)
        }

        binding.deleteBtn.setOnClickListener(View.OnClickListener { view ->
            AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_baseline_delete_24)
                .setTitle("Delete!!!")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                    if (note != null) {
                        viewModel.deleteNote(note)
                    }
                    dialogInterface?.dismiss()
                    finish()
                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface?.dismiss()
                })
                .create()
                .show()
        })

        binding.saveBtn.setOnClickListener(View.OnClickListener { view ->
            if (binding.noteEdt.text.toString().isNotEmpty()){
                val format = SimpleDateFormat("EEE, d MMM yyyy HH:mm a")
                val date = Date()

                if (i == 1){
                    val note = if (binding.titleEdt.text.toString().isNotEmpty()) {
                        Note(note?.id, binding.titleEdt.text.toString(), binding.noteEdt.text.toString(), format.format(date))
                    } else {
                        Note(note?.id, "", binding.noteEdt.text.toString(), format.format(date))
                    }

                    viewModel.updateNote(note)
                } else {
                    val note = Note(title = binding.titleEdt.text.toString(), content = binding.noteEdt.text.toString(), time = format.format(date))

                    viewModel.addNote(note)
                }
                finish()
            } else {
                Snackbar.make(binding.root, "Enter Note Description", Snackbar.LENGTH_LONG).show()
            }
        })
    }
}