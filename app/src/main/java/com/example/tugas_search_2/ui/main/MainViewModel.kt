package com.example.tugas_search_2.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tugas_search_2.database.Note
import com.example.tugas_search_2.repository.NoteRepository

class MainViewModel(application: Application) : ViewModel() {
    private val mNoteRepository: NoteRepository = NoteRepository(application)
    fun getAllNotes(): LiveData<List<Note>> = mNoteRepository.getAllNotes()
    fun getFavoriteUserById(userId: Int): LiveData<Note> = mNoteRepository.getFavoriteUserById(userId)
}