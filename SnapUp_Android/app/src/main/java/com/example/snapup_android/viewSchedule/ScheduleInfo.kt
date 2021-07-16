package com.example.snapup_android.viewSchedule

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.snapup_android.Http
import com.example.snapup_android.R
import com.example.snapup_android.data.gsonClass.EditScheduleGson
import com.example.snapup_android.TrainInfor
import com.google.android.material.card.MaterialCardView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_schedule_info.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.util.ArrayList


class ScheduleInfo : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    val trainId = "0"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_info)

        val trainId = intent.getStringExtra("TRAIN_ID") ?: ""
        val startingStation = intent.getStringExtra("STARTING_STATION")?: "i am starting station"
        val terminus = intent.getStringExtra("TERMINUS")?: "i am terminus"
        val time = intent.getStringExtra("TIME")?: "i am time"
        val stopovers = intent.getStringArrayListExtra("STOPOVERS")?:"i am stopovers" as ArrayList<String>

        val editSchedule = findViewById<Button>(R.id.edit_schudule)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        collapsingToolbar.title = trainId         //名字
        Glide.with(this).load(trainId).into(TrainImageView)

        TrainId.text = "车次号： $trainId"
        TrainBeginningStation.text = "始发站： $startingStation"
        TrainDestination.text = "终点站： $terminus"
        TrainTime.text = "发车时间： $time"
        if(stopovers.size > 3 ) StationList.text = "经停站： ${stopovers[0]} ... ${stopovers[stopovers.size-1]} "
        StationList.text = "经停站： $stopovers "


        editSchedule.setOnClickListener {
            //传参点击事件
            requestEditSchedule()
        }
        findViewById<MaterialCardView>(R.id.StationCard).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("经停站")
                .setMessage("经停站为$stopovers。")
                .create().show()
        }
    }

    fun requestEditSchedule(){
        val mime = "application/json;charset=utf-8".toMediaTypeOrNull()

        val json = JSONObject()
            .put("run_code", TrainInfor.trainId).toString()

        val request = Request.Builder()//创建Request 对象。
            .url("${Http.prefix}/api/train/before-submit-order/")
            .post(json.toRequestBody(mime)).build()//传递请求体
        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("UPDATE", "onFailure: $e")
                Looper.prepare()
                Toast.makeText(this@ScheduleInfo , "Http Request Failed", Toast.LENGTH_SHORT).show()
                Looper.loop()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                Looper.prepare()
                val editScheduleGson = Gson().fromJson(response.body?.string(), EditScheduleGson::class.java)
                Log.d("UPDATE", "OnResponse: $editScheduleGson")

//                else Toast.makeText(this@ScheduleInfo , "Login Failed", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@ScheduleInfo, TicketActivity::class.java)
                intent.putStringArrayListExtra("depart",editScheduleGson.depart as ArrayList<String>)
                intent.putStringArrayListExtra("arrive",editScheduleGson.arrive as ArrayList<String>)
                intent.putStringArrayListExtra("time",editScheduleGson.time as ArrayList<String>)
                startActivity(intent)
                //do something in mine thread
                Looper.loop()
            }
        })
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

