package com.example.snapup_android.viewOrder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.snapup_android.R
import com.example.snapup_android.User

class ViewOrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_order)
        setSupportActionBar(findViewById(R.id.toolbar))

        val bundle = intent.extras
        //数据
        User.username = bundle?.getString("username")!!
        User.password = bundle?.getString("password")!!
        User.identity = bundle?.getString("identity")!!
        User.gender = bundle?.getString("gender")!!
        User.name = bundle?.getString("name")!!
        User.number = bundle?.getString("number")!!
        User.mail = bundle?.getString("mail")!!
        User.nickname = bundle?.getString("nickname")!!

        val orderList = OrderList.newInstance("nothing")
    }
}