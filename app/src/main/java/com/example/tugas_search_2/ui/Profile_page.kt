package com.example.tugas_search_2.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.tugas_search_2.R
import com.example.tugas_search_2.data.response.ItemsItem
import com.example.tugas_search_2.data.retrofit.ApiConfig
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class profile_page : AppCompatActivity() {
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])

        }.attach()
        supportActionBar?.elevation = 0f




        var getData = Gson().fromJson(intent?.extras?.getString("detailUser"), ItemsItem::class.java)

        if (getData != null) {
            val detailTitle: TextView = findViewById(R.id.detailTitle)
            val detailImage: ImageView = findViewById(R.id.detailImage)
//            val detailDesc: TextView = findViewById(R.id.detailDesc)
            detailTitle.text = getData.login
            Glide.with(applicationContext)
                .load(getData.avatarUrl)
                .into(detailImage)


        }

    }}


