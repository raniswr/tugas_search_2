package com.example.tugas_search_2.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.tugas_search_2.database.Note
import com.example.tugas_search_2.database.NoteDao
import com.example.tugas_search_2.database.NoteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class NoteRepository(application: Application) {
    private val mNotesDao: NoteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = NoteRoomDatabase.getDatabase(application)
        mNotesDao = db.noteDao()
    }
    fun getAllNotes(): LiveData<List<Note>> = mNotesDao.getAllNotes()
    fun getFavoriteUserById(idUser: Int): LiveData<Note> = mNotesDao.getFavoriteUserById(idUser)

    fun getNotebyId(idUser: Int): Note = mNotesDao.getNoteById(idUser)
 fun delete(note: Note) {
        mNotesDao.delete(note)
    }
    fun deleteById(idUser: Int) {
        executorService.execute { mNotesDao.deleteById(idUser) }

    }



    fun insert(note: Note) {
        executorService.execute { mNotesDao.insert(note) }
    }

    fun update(note: Note) {
        executorService.execute { mNotesDao.update(note) }
    }
}