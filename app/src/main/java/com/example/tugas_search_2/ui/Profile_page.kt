package com.example.tugas_search_2.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
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
import com.example.tugas_search_2.ui.main.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
    private var isfav = true
    var getData1: ItemsItem? = null
    var getData2: Note? = null



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
        val gson = Gson()
        val extras = intent?.extras

        val data = if (extras != null) {
            when {
                extras.containsKey("detailUser") -> {
                  getData1  =  gson.fromJson(extras.getString("detailUser"), ItemsItem::class.java)
                }
                extras.containsKey("detailUser2") -> {
                    getData2=   gson.fromJson(extras.getString("detailUser2"), Note::class.java)
                }
                else -> {
                    // Handle the case where neither "detailUser" nor "detailUser2" is present.
                    // You might want to return a default value or throw an exception here.
                    null
                }
            }
        } else {
            // Handle the case where the intent's extras bundle is null.
            // You might want to return a default value or throw an exception here.
            null
        }






if (getData1!= null){

    val sectionsPagerAdapter = SectionsPagerAdapter(this, getData1?.login)


    val viewPager: ViewPager2 = findViewById(R.id.view_pager)
    viewPager.adapter = sectionsPagerAdapter

    val tabs: TabLayout = findViewById(R.id.tabs)
    binding.progressBar.visibility = View.INVISIBLE

    TabLayoutMediator(tabs, viewPager) { tab, position ->
        tab.text = resources.getString(TAB_TITLES[position])


    }.attach()
    supportActionBar?.elevation = 0f
    if (getData1 != null) {

        val detailImage: ImageView = findViewById(R.id.detailImage)
        val detailUsername: TextView = findViewById(R.id.detailUsername)
        detailUsername.text = getData1?.login

        findUser(getData1?.login!!)


        Glide.with(applicationContext)
            .load(getData1?.avatarUrl)
            .into(detailImage)
    }
    val mainViewModel1 = obtainViewModel1(this@profile_page)
    mainViewModel1.getFavoriteUserById(getData1?.id!!).observe(this) { noteList ->
        if ( noteList != null && noteList.idUser == getData1?.id)
        {
            isfav = true
            binding.fabAdd.setImageResource(R.drawable.ic_favorite_full)
        } else{
            isfav = false
            binding.fabAdd.setImageResource(R.drawable.ic_add)

        }
    }



    note = intent.getParcelableExtra(EXTRA_NOTE)
    if (note != null) {
        note = Note()
    } else {
        note = Note()
    }
    val actionBarTitle: String



    supportActionBar?.setDisplayHomeAsUpEnabled(true)



    binding?.fabAdd?.setOnClickListener {









        when {
            !isfav -> {
                binding.fabAdd.setImageResource(R.drawable.ic_favorite_full)
                val title = binding?.detailUsername?.text.toString()
                val description = getData1?.avatarUrl
                val idUser = getData1?.id
                note.let { note ->
                    note?.title = title
                    note?.description = description
                    note?.idUser = idUser
                }
                noteAddUpdateViewModel.insert(note as Note)

            }

            else -> {
                binding.fabAdd.setImageResource(R.drawable.ic_add)
                noteAddUpdateViewModel.deleteById(getData1?.id!!)
            }



        }

//            val intent = Intent(this@profile_page, FavoritePage::class.java)
//            startActivity(intent)
    }

} else{



    val sectionsPagerAdapter = SectionsPagerAdapter(this, getData2?.title)


    val viewPager: ViewPager2 = findViewById(R.id.view_pager)
    viewPager.adapter = sectionsPagerAdapter

    val tabs: TabLayout = findViewById(R.id.tabs)
    binding.progressBar.visibility = View.INVISIBLE

    TabLayoutMediator(tabs, viewPager) { tab, position ->
        tab.text = resources.getString(profile_page.TAB_TITLES[position])


    }.attach()
    supportActionBar?.elevation = 0f
    if (getData2 != null) {

        val detailImage: ImageView = findViewById(R.id.detailImage)
        val detailUsername: TextView = findViewById(R.id.detailUsername)
        detailUsername.text = getData2?.title

        findUser(getData2?.title!!)


        Glide.with(applicationContext)
            .load(getData2?.description)
            .into(detailImage)
    }

    binding.fabAdd.setImageResource(R.drawable.ic_favorite_full)




    note = intent.getParcelableExtra(DetailPage.EXTRA_NOTE)
    if (note != null) {
        note = Note()
    } else {
        note = Note()
    }
    val actionBarTitle: String



    supportActionBar?.setDisplayHomeAsUpEnabled(true)



    binding?.fabAdd?.setOnClickListener {

        when {
            isfav -> {
                binding.fabAdd.setImageResource(R.drawable.ic_add)
                noteAddUpdateViewModel.deleteById(getData2?.idUser!!)
            }

            else -> {


                binding.fabAdd.setImageResource(R.drawable.ic_favorite_full)
                val title = binding?.detailUsername?.text.toString()
                val description = getData2?.description
                val idUser = getData2?.idUser
                note.let { note ->
                    note?.title = title
                    note?.description = description
                    note?.idUser = idUser
                }
                noteAddUpdateViewModel.insert(note as Note)
            }



        }

//            val intent = Intent(this@profile_page, FavoritePage::class.java)
//            startActivity(intent)
    }

}

     }


    private fun obtainViewModel(activity: AppCompatActivity): NoteAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(NoteAddUpdateViewModel::class.java)
    }
    private fun obtainViewModel1(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
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

private fun FloatingActionButton.setCompatElevationResource(elevationResource: Int) {
    val fab = findViewById<FloatingActionButton>(R.id.fab_add)
    fab.setCompatElevationResource(com.google.android.material.R.dimen.abc_action_bar_content_inset_with_nav)
    ViewCompat.setElevation(this, resources.getDimension(elevationResource))

}


