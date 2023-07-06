package com.example.notekeeper.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.notekeeper.AddActivity
import com.example.notekeeper.R
import com.example.notekeeper.databinding.NoteItemBinding
import com.example.notekeeper.model.Note
import com.google.android.material.snackbar.Snackbar
import java.util.ArrayList

class NoteAdapter(val context: Context, var noteList: List<Note>): RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.note_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.noteTime.isSelected = true
        if (noteList[position].title.equals("")){
            holder.binding.noteTitle.visibility = View.GONE
        } else {
            holder.binding.noteTitle.text = noteList[position].title
        }
        holder.binding.noteContent.text = noteList[position].content
        holder.binding.noteTime.text = noteList[position].time

        holder.itemView.setOnClickListener(View.OnClickListener { view ->
            val intent = Intent(context, AddActivity::class.java)
            intent.putExtra("key", 1)
            intent.putExtra("data", noteList[holder.adapterPosition])
            context.startActivity(intent)
        })

        holder.itemView.setOnLongClickListener(View.OnLongClickListener { view ->
            val popup = PopupMenu(context, holder.itemView)
            popup.inflate(R.menu.popup_menu)
            if (noteList[holder.adapterPosition].title.equals("")){
                popup.menu.findItem(R.id.popup_copy_title).isVisible = false
            }

            popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { menuItem ->
                when(menuItem.itemId){
                    R.id.popup_copy_title -> {
                        val cm: ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val cd = ClipData.newPlainText("text", noteList[holder.adapterPosition].title)
                        cm.setPrimaryClip(cd)

                        Snackbar.make(holder.itemView, "Copied to clipboard", Snackbar.LENGTH_SHORT).show()
                    }

                    R.id.popup_copy_content -> {
                        val cm: ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val cd = ClipData.newPlainText("text", noteList[holder.adapterPosition].content)
                        cm.setPrimaryClip(cd)

                        Snackbar.make(holder.itemView, "Copied to clipboard", Snackbar.LENGTH_SHORT).show()
                    }
                }

                true
            })

            popup.show()

            true
        })
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun filteredList(arrayList: ArrayList<Note>) {
        noteList = arrayList
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding: NoteItemBinding = NoteItemBinding.bind(itemView)
    }
}