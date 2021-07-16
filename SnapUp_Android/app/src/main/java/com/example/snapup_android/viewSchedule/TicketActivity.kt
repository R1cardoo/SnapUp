package com.example.snapup_android.viewSchedule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.snapup_android.Http
import com.example.snapup_android.PriceInfo
import com.example.snapup_android.R
import com.example.snapup_android.R.id
import com.example.snapup_android.R.layout
import com.example.snapup_android.User
import com.example.snapup_android.TrainInfor
import com.example.snapup_android.data.gsonClass.EditScheduleGson
import com.example.snapup_android.data.gsonClass.PriceGson
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.angmarch.views.NiceSpinner
import org.angmarch.views.OnSpinnerItemSelectedListener
import org.json.JSONObject
import java.io.IOException

class TicketActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)

        val stationList= intent.getStringArrayListExtra("depart") as ArrayList<String>
        val timeList = intent.getStringArrayListExtra("time") as ArrayList<String>
        val seatList: Array<String> = arrayOf("1", "2")

        val adapterStation = ArrayAdapter<String>(this, layout.activity_ticket, stationList)
        adapterStation.setDropDownViewResource(layout.activity_search)
        val adapterTime = ArrayAdapter<String>(this, layout.activity_ticket, timeList)
        adapterTime.setDropDownViewResource(layout.activity_search)
        val adapterSeat = ArrayAdapter<String>(this, layout.activity_ticket, seatList)
        adapterSeat.setDropDownViewResource(layout.activity_search)

        findViewById<NiceSpinner>(id.start_Station).setAdapter(adapterStation)
        findViewById<NiceSpinner>(id.start_Station).onSpinnerItemSelectedListener = OnSpinnerItemSelectedListener{ _: NiceSpinner, view: View, _: Int, _: Long ->
            fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem: String = parent.getItemAtPosition(position).toString()
                Toast.makeText(baseContext, selectedItem, Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<NiceSpinner>(id.terminus).setAdapter(adapterStation)
        findViewById<NiceSpinner>(id.terminus).onSpinnerItemSelectedListener = OnSpinnerItemSelectedListener{ _: NiceSpinner, view: View, _: Int, _: Long ->
            fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem: String = parent.getItemAtPosition(position).toString()
                Toast.makeText(baseContext, selectedItem, Toast.LENGTH_SHORT).show()
            }
        }
        findViewById<NiceSpinner>(id.time).setAdapter(adapterTime)
        findViewById<NiceSpinner>(id.time).onSpinnerItemSelectedListener = OnSpinnerItemSelectedListener{ _: NiceSpinner, view: View, _: Int, _: Long ->
            fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem: String = parent.getItemAtPosition(position).toString()
                Toast.makeText(baseContext, selectedItem, Toast.LENGTH_SHORT).show()
            }
        }
        findViewById<NiceSpinner>(id.seat_kind).setAdapter(adapterSeat)
        findViewById<NiceSpinner>(id.seat_kind).onSpinnerItemSelectedListener = OnSpinnerItemSelectedListener{ _: NiceSpinner, view: View, _: Int, _: Long ->
            fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem: String = parent.getItemAtPosition(position).toString()
                Toast.makeText(baseContext, selectedItem, Toast.LENGTH_SHORT).show()
            }
        }
        findViewById<Button>(id.buy).setOnClickListener {
            requestPay()
        }

    }

    fun requestPay(){
        val mime = "application/json;charset=utf-8".toMediaTypeOrNull()

        val json = JSONObject()
            .put("run_code",TrainInfor.trainId)
            .put("date",findViewById<NiceSpinner>(id.time).text.toString())
            .put("depart_station_name",findViewById<NiceSpinner>(id.start_Station).text.toString())
            .put("arrival_station_name",findViewById<NiceSpinner>(id.terminus).text.toString())
            .put("username", User.username)
            .put("seat_type" , findViewById<NiceSpinner>(id.seat_kind).text.toString()).toString()

        val request = Request.Builder()//创建Request 对象。
            .url("${Http.prefix}/api/train/submit-order/")
            .post(json.toRequestBody(mime)).build()//传递请求体

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("UPDATE", "onFailure: $e")
                Looper.prepare()
                Toast.makeText(this@TicketActivity,"支付失败",Toast.LENGTH_SHORT).show()
                //do something in mine thread
                Looper.loop()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val priceGson = Gson().fromJson(response.body?.string(), PriceGson::class.java)
                Log.d("UPDATE", "OnResponse: " + priceGson)
                Looper.prepare()
                val builder = AlertDialog.Builder(this@TicketActivity)
                if(priceGson.price == 0.0F){
                    Toast.makeText(this@TicketActivity,"数据非法",Toast.LENGTH_SHORT).show()
                }else{
                builder.setTitle("支付")
                    .setMessage("价格是 ${priceGson.price}")    //补充价格
                    .setPositiveButton("提交"
                    ) { dialog, id ->
                        // 支付网络请求
                        Toast.makeText(this@TicketActivity,"支付成功",Toast.LENGTH_SHORT).show()

                    }
                    .setNegativeButton("取消"
                    ) { dialog, id ->
                        // 取消
                        Toast.makeText(this@TicketActivity,"取消支付",Toast.LENGTH_SHORT).show()
                    }
                    .create().show()
                }
                //do something in mine thread
                Looper.loop()
            }
        })
    }
}