package com.example.snapup_android.viewOrder

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.snapup_android.R
import com.example.snapup_android.viewInfo.ScheduleList

class ViewOrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_order)
        setSupportActionBar(findViewById(R.id.toolbar))

        val orderFragment = OrderFragment.newInstance(5)
    }
}