package com.example.snapup_android.viewSchedule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.snapup_android.R


class ViewScheduleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_schedule)
        setSupportActionBar(findViewById(R.id.toolbar))

        val scheduleList = ScheduleList.newInstance(5)


    }
}