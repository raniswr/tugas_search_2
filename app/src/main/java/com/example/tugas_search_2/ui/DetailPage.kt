package com.example.tugas_search_2.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tugas_search_2.R
import com.example.tugas_search_2.data.response.DetailResponse
import com.example.tugas_search_2.data.retrofit.ApiConfig
import com.example.tugas_search_2.database.Note
import com.example.tugas_search_2.databinding.ActivityDetailPageBinding
import com.example.tugas_search_2.ui.insert.NoteAddUpdateViewModel
import com.example.tugas_search_2.ui.insert.ViewModelFactory
import com.example.tugas_search_2.ui.main.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPage : AppCompatActivity() {
    private lateinit var binding : ActivityDetailPageBinding

    var jumlahFollowersDetail = ""
    var jumlahFollowingDetail= ""
    var nama = ""
    private var isEdit = false
    private var note: Note? = null
    private lateinit var noteAddUpdateViewModel: NoteAddUpdateViewModel
    private var isfav = true


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
        binding = ActivityDetailPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

