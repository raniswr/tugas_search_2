package com.example.tugas_search_2.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tugas_search_2.database.Note
import com.example.tugas_search_2.databinding.ItemFavoriteBinding
import com.example.tugas_search_2.helper.NoteDiffCallback
import com.example.tugas_search_2.ui.profile_page

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private val listNotes = ArrayList<Note>()
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
        holder.bind(listNotes[position])
    }
    override fun getItemCount(): Int {
        return listNotes.size
    }
    inner class NoteViewHolder(private val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            with(binding) {
                tvItemTitle.text = note.title
                val detailImage: ImageView = binding.profilFavorite
                Glide.with(binding.profilFavorite)
                    .load("${note.description}")
                    .into(binding.profilFavorite)

                cvItemNote.setOnClickListener {
                    val intent = Intent(it.context, profile_page::class.java)
                    intent.putExtra(profile_page.EXTRA_NOTE, note)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}