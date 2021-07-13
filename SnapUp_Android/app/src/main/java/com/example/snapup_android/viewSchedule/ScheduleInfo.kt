package com.example.snapup_android.viewSchedule

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.snapup_android.R
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.activity_schedule_info.*


class ScheduleInfo : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_info)

        val trainId = intent.getStringExtra("TRAIN_ID") ?: ""
        val startingStation = intent.getStringExtra("STARTING_STATION")?: "i am starting station"
        val terminus = intent.getStringExtra("TERMINUS")?: "i am terminus"
        val time = intent.getStringExtra("TIME")?: "i am time"
        val stopovers = intent.getStringArrayListExtra("STOPOVERS")?:"i am stopovers"//经停站

        val editSchedule = findViewById<Button>(R.id.edit_schudule)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        collapsingToolbar.title = trainId         //名字
        Glide.with(this).load(trainId).into(TrainImageView)

        TrainId.text = "车次号： $trainId"
        TrainBeginningStation.text = "始发站： $startingStation"
        TrainDestination.text = "终点站： $terminus"
        TrainTime.text = "发车时间： $time"
        StationList.text = "经停站：$stopovers ..."


        editSchedule.setOnClickListener {
            //传参点击事件
            val intent = Intent(this, TicketActivity::class.java)
            startActivity(intent)
        }
        findViewById<MaterialCardView>(R.id.StationCard).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("经停站")
                .setMessage("经停站为$stopovers。")
                .create().show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}

