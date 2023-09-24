package com.example.tugas_search_2.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import com.example.tugas_search_2.data.response.ItemsItem
import com.example.tugas_search_2.data.retrofit.ApiConfig
import com.example.tugas_search_2.databinding.FragmentFollowersBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FollowersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowersFragment : Fragment() {
    // TODO: Rename and change types of parameters
    var KEY_ACTIVITY: String? = "msg_activity"

    var txtMessageF: TextView? = null
    var edtMessageF: EditText? = null
    var btn_Send: Button? = null
    private var position: Int? = null
    private var username: String? = null

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_followers, container, false)
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
         return binding.root


    }

    private fun findFollower(username: String?, context: Context?) {

        val client = ApiConfig.getApiService().getFollowers(username= username!!)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {

                        setUserData(responseBody, context)
                    }
                } else {

                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {

            }
        })
    }

    private var dataUsers: List<ItemsItem?>? = null
    private fun setUserData(itemUser: List<ItemsItem>, context: Context?) {
        val adapter = ListFollowerAdapter( itemUser, requireContext())
        adapter.submitList(itemUser)
        binding.rvFollower.adapter = adapter
        dataUsers = itemUser
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        findFollower(username, context)
//        if (position == 1){
//            binding.textFollower.text= "Get Follower $username"
//        } else {
//            binding.textFollower.text = "Get Following $username"
//        }

    }


    companion object {
        const val ARG_POSITION = "app_name"
        const val ARG_USERNAME = "username"
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FollowersFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FollowersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_POSITION, param1)
                    putString(ARG_USERNAME, param2)
                }
            }
    }
}