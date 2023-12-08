package com.example.tugas_search_2.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Note)
    @Update
    fun update(note: Note)
    @Delete
    fun delete(note: Note)
    @Query("SELECT * from note ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Note>>
    @Query("SELECT * FROM note WHERE idUser = :idUser")
    fun getFavoriteUserById(idUser: Int): LiveData<Note>
    @Query("DELETE FROM note WHERE idUser = :idUser")
    fun deleteById(idUser: Int)
    @Query("SELECT * FROM note WHERE idUser = :idUser")
    fun getNoteById(idUser: Int): Note

}