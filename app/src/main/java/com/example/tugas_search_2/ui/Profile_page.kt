package com.example.tugas_search_2.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.tugas_search_2.R
import com.example.tugas_search_2.data.response.DetailResponse
import com.example.tugas_search_2.data.response.ItemsItem
import com.example.tugas_search_2.data.retrofit.ApiConfig
import com.example.tugas_search_2.database.Note
import com.example.tugas_search_2.databinding.ActivityProfilePageBinding
import com.example.tugas_search_2.ui.insert.NoteAddUpdateViewModel
import com.example.tugas_search_2.ui.insert.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class profile_page : AppCompatActivity() {
    private lateinit var binding : ActivityProfilePageBinding

    var jumlahFollowersDetail = ""
    var jumlahFollowingDetail= ""
    var nama = ""
    private var isEdit = false
    private var note: Note? = null
    private lateinit var noteAddUpdateViewModel: NoteAddUpdateViewModel


    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilePageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        noteAddUpdateViewModel = obtainViewModel(this@profile_page)
        var getData = Gson().fromJson(intent?.extras?.getString("detailUser"), ItemsItem::class.java)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, getData.login)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        binding.progressBar.visibility = View.INVISIBLE

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])


        }.attach()
        supportActionBar?.elevation = 0f
        if (getData != null) {

            val detailImage: ImageView = findViewById(R.id.detailImage)
            val detailUsername: TextView = findViewById(R.id.detailUsername)
            detailUsername.text = getData.login
            findUser(getData.login)

            Glide.with(applicationContext)
                .load(getData.avatarUrl)
                .into(detailImage)
        }
        note = intent.getParcelableExtra(EXTRA_NOTE)
        if (note != null) {
            isEdit = true
        } else {
            note = Note()
        }
        val actionBarTitle: String

        if (isEdit) {
            actionBarTitle = getString(R.string.change)

            if (note != null) {
                note?.let { note ->
                    binding?.detailUsername?.setText(note.title)

                }
            }
        } else {
            actionBarTitle = getString(R.string.add)

        }
        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.fabAdd?.setOnClickListener {

binding.fabAdd.setColorFilter(200)

//            val myUrl = "${note?.description}"

            val title = "@${binding?.detailUsername?.text.toString()}"
            val description = getData.avatarUrl
//            val detailImage: ImageView = findViewById(R.id.detailImage)
//            Glide.with(getApplicationContext())
//                .load(myUrl)
//                .into(detailImage)


                note.let { note ->
                    note?.title = title

                note?.description = description
                }
                noteAddUpdateViewModel.insert(note as Note)


//            val intent = Intent(this@profile_page, FavoritePage::class.java)
//            startActivity(intent)
        }

     }


    private fun obtainViewModel(activity: AppCompatActivity): NoteAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(NoteAddUpdateViewModel::class.java)
    }


    private fun findUser(username: String) {
        binding.progressBar.visibility = View.VISIBLE


        val client = ApiConfig.getApiService().getDetail(username=username)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                       setUserData(responseBody)
                    }
                } else {

                }
            }
            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {


            }
        })



    }

    private fun setUserData(detailResponse: DetailResponse) {
        binding.progressBar.visibility = View.INVISIBLE
        jumlahFollowersDetail=detailResponse.followers.toString()

        jumlahFollowingDetail=detailResponse.following.toString()
        nama = detailResponse.name.toString()
        val detailTitle: TextView = findViewById(R.id.detailTitle)
        val jumlahFollower:TextView = findViewById(R.id.jumlahFollower)
        val jumlahFollowing:TextView = findViewById(R.id.jumlahFollowing)
detailTitle.text = nama
        jumlahFollower.text = jumlahFollowersDetail + " Folowers"
        jumlahFollowing.text = jumlahFollowingDetail+ " Folowing"
    }






}


