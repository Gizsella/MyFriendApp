package com.example.myfriendapp

import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfriendsapp.AppDatabase
import com.example.myfriendsapp.MainActivity
import com.example.myfriendsapp.MyFriendDao
import com.example.myfriendsapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MyFriendsFragment : Fragment() {
    private var fabAddFriend: FloatingActionButton? = null
//    private var listTeman : MutableList<MyFriend>? = null
    private var listTeman: List<MyFriend>? = null
    private var listMyFriends : RecyclerView? = null
    private var db: AppDatabase? = null
    private var myFriendDao: MyFriendDao? = null

    companion object {
        fun newInstance(): MyFriendsFragment {
            return MyFriendsFragment()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_friends_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLocalDB()
        initView()
    }

    private fun initLocalDB() {
        db = AppDatabase.getAppDataBase(requireActivity())
        myFriendDao = db?.myFriendDao()
    }

    private fun initView() {
        listMyFriends = activity?.findViewById(R.id.listMyFriends)
        fabAddFriend = activity?.findViewById(R.id.fabAddFriend)
        fabAddFriend?.setOnClickListener {
            (activity as MainActivity).tampilMyFriendsAddFragment()
        }
//        simulasiDataTeman()
//        tampilTeman()
        ambilDataTeman()
    }

    private fun ambilDataTeman() {
        listTeman = ArrayList()
        myFriendDao?.ambilSemuaTeman()?.observe(requireActivity(), Observer { r -> listTeman = r
            when {
                listTeman?.size == 0 -> tampilToast("Belum ada data teman")
                else -> {
                    tampilTeman()
                }
            }
        })
    }

    private fun tampilToast(message: String){
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        fabAddFriend = null
        listMyFriends = null
    }

    private fun tampilTeman() {
        listMyFriends?.layoutManager = LinearLayoutManager(activity)
        listMyFriends?.adapter = MyFriendAdapter(requireActivity(),
            listTeman!! as ArrayList<MyFriend>
        )
    }

    private fun simulasiDataTeman() {
        listTeman = ArrayList()
//        listTeman.add(MyFriend("Muhammad", "Laki-laki", "ade@gmail.com", "085719004268", "Bandung"))
//        listTeman.add(MyFriend("Al Harits", "Laki-laki", "rifaldi@gmail.com", "081213416171", "Bandung"))
    }
}

