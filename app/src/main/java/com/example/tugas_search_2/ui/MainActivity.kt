package com.example.tugas_search_2.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugas_search_2.data.response.ItemsItem
import com.example.tugas_search_2.data.response.SearchResponse
import com.example.tugas_search_2.data.retrofit.ApiConfig
import com.example.tugas_search_2.databinding.ActivityMainBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    companion object {
        private const val TAG = "MainActivity"
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.searchView.setupWithSearchBar(binding.searchBar)
        setContentView(binding.root)
        supportActionBar?.hide()
        val layoutManager = LinearLayoutManager(this)
        binding.rvReview.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)
        binding?.buttonFavorite?.setOnClickListener {
            val intent = Intent(this@MainActivity, FavoritePage::class.java)
            startActivity(intent)
        }
        findUser("a")


       fun onQueryTextSubmit(query:String?): Boolean {
            binding.searchView.clearFocus()
            findUser(query!!)
            return true
        }

        // When search view text change
        fun onQueryTextChange(newText: String?): Boolean {
            val searchText = newText!!.toLowerCase(Locale.getDefault())
            if (searchText.isNotEmpty()){
                findUser(searchText!!)
            }
            return false
        }
        binding.searchView
            .editText
            .setOnEditorActionListener { textView, actionId, event ->
                binding.searchBar.text = binding.searchView.text
val query = binding.searchView.text.toString()
                binding.searchView.hide()
                //todo panggil function untuk mengambil data
              onQueryTextSubmit(query)
                onQueryTextChange(textView.toString())

                Toast.makeText(this@MainActivity, binding.searchView.text, Toast.LENGTH_SHORT)
                    .show()


                false
            }
    }


    private fun showLoading(isLoading: Boolean) {}

    private fun findUser(keyword: String) {
        binding.progressBar.visibility = View.VISIBLE
        val client = ApiConfig.getApiService().getAllUsers(keyword=keyword)
        client.enqueue(object : Callback<SearchResponse>{
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {

                        setUserData(responseBody.items)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })

    }

    private var dataUsers: List<ItemsItem?>? = null
    private fun setUserData(itemUser: List<ItemsItem>) {
        binding.progressBar.visibility = View.INVISIBLE
        val adapter = AkunAdapter(dataUsers,applicationContext)
        adapter.submitList(itemUser)
        binding.rvReview.adapter = adapter
        dataUsers = itemUser



        adapter.onItemClick = {
            val intent = Intent(this, profile_page::class.java)
            intent.putExtra("detailUser", Gson().toJson(it))
            startActivity(intent)
        }
    }
    }



