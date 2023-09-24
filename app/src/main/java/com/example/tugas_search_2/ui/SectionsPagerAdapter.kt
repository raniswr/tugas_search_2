package com.example.tugas_search_2.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.example.tugas_search_2.data.response.ItemsItem
import com.example.tugas_search_2.databinding.ItemFollowersBinding

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var appName: String = "haloo"




    override fun getItemCount(): Int {
        return 2
    }
    override fun createFragment(position: Int): Fragment {
        val fragment = FollowersFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowersFragment.ARG_POSITION, position + 1)
            putString(FollowersFragment.ARG_USERNAME, appName)
        }
        return fragment
    }
    class MyViewHolder(val binding: ItemFollowersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: ItemsItem){
            binding.namaFollowers.text = "${review.login}\n- ${review.followersUrl}"
            Glide.with(binding.profilFollower)
                .load("${review.avatarUrl}")
                .into(binding.profilFollower)
        }
    }

//    override fun createFragment(position: Int): Fragment {
////        val sectionsPagerAdapter = SectionsPagerAdapter(this)
////        sectionsPagerAdapter.appName = resources.getString(R.string.app_name)
//
////        var fragment: Fragment? = null
////        when (position) {
////            0 -> fragment = FollowersFragment()
////            1 -> fragment = FollowingFragment()
////        }
////        return fragment as Fragment
//
//
//    }




}