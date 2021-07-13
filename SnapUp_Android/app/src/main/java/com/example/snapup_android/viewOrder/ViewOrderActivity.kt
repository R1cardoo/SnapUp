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

        val orderList = OrderList.newInstance("nothing")
    }
}