package com.example.tugas_search_2.data.retrofit
import com.example.tugas_search_2.data.response.DetailResponse
import com.example.tugas_search_2.data.response.ItemsItem
import com.example.tugas_search_2.data.response.SearchResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {


    @GET("search/users")
    fun getAllUsers(
        @Query("q") keyword: String,
    ): Call<SearchResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}")
    fun getDetail(@Path("username") username: String): Call<DetailResponse>


}