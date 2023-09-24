package com.example.tugas_search_2.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tugas_search_2.data.response.ItemsItem
import com.example.tugas_search_2.databinding.ItemFollowersBinding

class ListFollowerAdapter(private val dataList: List<ItemsItem>,var context : Context): ListAdapter<ItemsItem,ListFollowerAdapter.MyViewHolder>(ListFollowerAdapter.DIFF_CALLBACK) {
    var onItemClick: ((ItemsItem?) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFollowerAdapter.MyViewHolder {
        val binding = ItemFollowersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val login = getItem(position)
        holder.bind(login)
        holder.itemView.setOnClickListener{
            onItemClick?.invoke(login)
        }
    }
    class MyViewHolder(val binding: ItemFollowersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: ItemsItem){
            binding.namaFollowers.text = "${review.login}\n- ${review.followersUrl}"
            Glide.with(binding.profilFollower)
                .load("${review.avatarUrl}")
                .into(binding.profilFollower)
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}


