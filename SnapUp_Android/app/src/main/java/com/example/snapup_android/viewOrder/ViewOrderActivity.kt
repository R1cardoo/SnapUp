package com.example.snapup_android.viewOrder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.snapup_android.R

class ViewOrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_order)
        setSupportActionBar(findViewById(R.id.toolbar))

        val bundle = intent.extras
        //数据
        val username = bundle?.getString("username")
        val password = bundle?.getString("password")
        val identity = bundle?.getString("identity")
        val gender = bundle?.getString("gender")
        val name = bundle?.getString("name")
        val number = bundle?.getString("number")
        val mail = bundle?.getString("mail")
        val nickname = bundle?.getString("nickname")

        val orderFragment = OrderList.newInstance(5)
    }
}