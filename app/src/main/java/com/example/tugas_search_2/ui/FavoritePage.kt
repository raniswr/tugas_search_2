package com.example.tugas_search_2.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugas_search_2.databinding.ActivityFavoritePageBinding
import com.example.tugas_search_2.ui.insert.ViewModelFactory
import com.example.tugas_search_2.ui.main.MainViewModel
import com.example.tugas_search_2.ui.main.NoteAdapter
import com.google.gson.Gson

class FavoritePage : AppCompatActivity() {

    private var _activityMainBinding: ActivityFavoritePageBinding? = null
    private val binding get() = _activityMainBinding

    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityMainBinding = ActivityFavoritePageBinding.inflate(layoutInflater)
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
        adapter.onItemClick = {
            val intent = Intent(this, profile_page::class.java)
            intent.putExtra("detailUser2", Gson().toJson(it))
            startActivity(intent)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }
}
