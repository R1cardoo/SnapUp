package com.example.snapup_android.viewOrder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.snapup_android.R

class ViewOrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_order)
        val bundle = intent.getBundleExtra("data")
        setSupportActionBar(findViewById(R.id.toolbar))

        val orderFragment = OrderList.newInstance(5)
    }
}