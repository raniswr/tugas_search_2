package com.example.tugas_search_2.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugas_search_2.databinding.ActivityFavoritePageBinding
import com.example.tugas_search_2.ui.insert.ViewModelFactory
import com.example.tugas_search_2.ui.main.MainViewModel
import com.example.tugas_search_2.ui.main.NoteAdapter

class FavoritePage : AppCompatActivity() {
    private var _activityFavoriteBinding: ActivityFavoritePageBinding? = null
    private val binding get() = _activityFavoriteBinding
    private lateinit var adapter: NoteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        _activityFavoriteBinding = ActivityFavoritePageBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val mainViewModel = obtainViewModel(this@FavoritePage)
        mainViewModel.getAllNotes().observe(this) { noteList ->
            if (noteList != null) {
                adapter.setListNotes(noteList)
            }
        }
        adapter = NoteAdapter()
        binding?.rvNotes?.layoutManager = LinearLayoutManager(this)
        binding?.rvNotes?.setHasFixedSize(true)
        binding?.rvNotes?.adapter = adapter


    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }
    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteBinding = null
    }
}
