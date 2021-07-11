package com.example.snapup_android.viewSchedule

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.snapup_android.R
import kotlinx.android.synthetic.main.activity_order_info.TrainBeginningStation
import kotlinx.android.synthetic.main.activity_order_info.TrainDestination
import kotlinx.android.synthetic.main.activity_order_info.TrainId
import kotlinx.android.synthetic.main.activity_order_info.TrainTime
import kotlinx.android.synthetic.main.activity_schedule_info.TrainImageView
import kotlinx.android.synthetic.main.activity_schedule_info.collapsingToolbar
import java.util.ArrayList

class ScheduleInfo : AppCompatActivity() {

    private val TRAIN_ID = ""
    private val STARTING_STATION = ""
    private val TERMINUS = ""
    private val TIME = ""
    val STOPOVERS : List<String> = ArrayList()              //经停站



    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_info)

        val trainId = intent.getStringExtra(TRAIN_ID) ?: ""
        val startingStation = intent.getStringExtra(STARTING_STATION)?: "i am starting station"
        val terminus = intent.getStringExtra(TERMINUS)?: "i am terminus"
        val time = intent.getStringExtra(TIME)?: "i am time"
        //val stopovers = intent.getStringArrayListExtra()//经停站

        val editSchedule = findViewById<Button>(R.id.edit_schudule)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        collapsingToolbar.title = trainId         //名字
        Glide.with(this).load(trainId).into(TrainImageView)

        TrainId.text = "车次号： $trainId"
        TrainBeginningStation.text = "始发站：$startingStation"
        TrainDestination.text = "终点站：$terminus"
        TrainTime.text = "发车时间： $time"


        editSchedule.setOnClickListener {
            //传参
            val intent = Intent(this, TicketActivity::class.java)
            startActivity(intent)
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

