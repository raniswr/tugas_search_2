package com.example.tugas_search_2.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tugas_search_2.database.Note
import com.example.tugas_search_2.databinding.ItemFavoriteBinding
import com.example.tugas_search_2.helper.NoteDiffCallback


class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private val listNotes = ArrayList<Note>()
    var onItemClick: ((Note?) -> Unit)? = null
    fun setListNotes(listNotes: List<Note>) {
        val diffCallback = NoteDiffCallback(this.listNotes, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listNotes.clear()
        this.listNotes.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val login = listNotes[position]

        holder.bind(login)
        holder.itemView.setOnClickListener{
            onItemClick?.invoke(login)
        }

    }
    override fun getItemCount(): Int {
        return listNotes.size
    }
    inner class NoteViewHolder(private val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            with(binding) {
                tvItemTitle.text = note.title
                Glide.with(binding.profilFavorite)
                    .load("${note.description}")
                    .into(binding.profilFavorite)
                val cvItemNote = binding.cvItemNote
//                cvItemNote.setOnClickListener {
//                    val intent = Intent(it.context, profile_page::class.java)
//                    intent.putExtra("detailUser", Gson().toJson(it))
//                    it.context.startActivity(intent)
//                }
            }
        }
    }

}