package com.example.tugas_search_2.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.tugas_search_2.database.Note
import com.example.tugas_search_2.repository.NoteRepository

class NoteAddUpdateViewModel(application: Application) : ViewModel() {
    private val mNoteRepository: NoteRepository = NoteRepository(application)
    fun insert(note: Note) {
        mNoteRepository.insert(note)
    }
    fun update(note: Note) {
        mNoteRepository.update(note)
    }
    fun delete(note: Note) {
        mNoteRepository.delete(note)
    }
    fun deleteById(idUser: Int) {
        mNoteRepository.deleteById(idUser)
    }


}