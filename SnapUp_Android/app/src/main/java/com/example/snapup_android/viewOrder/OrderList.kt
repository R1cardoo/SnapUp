package com.example.snapup_android.viewOrder

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.snapup_android.Http
import com.example.snapup_android.R
import com.example.snapup_android.TrainInfor
import com.example.snapup_android.User
import com.example.snapup_android.data.gsonClass.OrderInfoGson
import com.example.snapup_android.data.gsonClass.ScheduleInfoGson
import com.example.snapup_android.viewOrder.MyOrderRecyclerViewAdapter.OnOrderClickListener
import com.example.snapup_android.viewOrder.content.OrderContentList
import com.example.snapup_android.viewSchedule.ScheduleInfo
import com.example.snapup_android.viewSchedule.content.ScheduleContentList
import com.google.gson.Gson
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

/**
 * A fragment representing a list of Items.
 */
class OrderList : Fragment() {

    private var columnCount = 1

    var stopovers = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //在这里调用 通过User请求一下order list


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order_item_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyOrderRecyclerViewAdapter(OrderContentList.ITEMS)
                (adapter as MyOrderRecyclerViewAdapter).setOnOrderClickListener(object : OnOrderClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        //点击事件 弹出详情
                        requestOrderInfo(position)
                    }
                })
                view.adapter = adapter
            }
        }
        return view
    }

    companion object {

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(username: String?) =
            OrderList().apply {
                arguments = Bundle().apply {
                    putString("username", username)

                }
            }
    }
    fun requestOrderInfo(position: Int) {
        val mime = "application/json;charset=utf-8".toMediaTypeOrNull()

        val json = JSONObject()
            .put("run_code", OrderContentList.ITEMS[position].run_code)
            .put("depart_time", OrderContentList.ITEMS[position].departure_time)
            .put("depart", OrderContentList.ITEMS[position].start_station_name)
            .put("arrive", OrderContentList.ITEMS[position].end_station_name)
            .put("order", OrderContentList.ITEMS[position].order).toString()
        try {
            val request = Request.Builder()//创建Request 对象。
                .url("${Http.prefix}/api/train/order-info-detail/")
                .post(json.toRequestBody(mime)).build()//传递请求体

            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("UPDATE", "onFailure: $e")
                    Looper.prepare()
                    Toast.makeText(activity , "Http Request Failed", Toast.LENGTH_SHORT).show()
                    Looper.loop()
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {

                    try {
                        val orderInfoGson = Gson().fromJson(response.body?.string(), OrderInfoGson::class.java)
                        Log.d("UPDATE", "OnResponse: $orderInfoGson")
                        stopovers = orderInfoGson.data as ArrayList<String>

                        val bundle = Bundle()
                        //向bundle填入方法返回的信息
                        bundle.putString("run_code", orderInfoGson.run_code)
                        bundle.putString("depart_time", orderInfoGson.depart_time)
                        bundle.putString("depart", orderInfoGson.depart)
                        bundle.putString("arrive", orderInfoGson.arrive)
                        bundle.putStringArrayList("data", orderInfoGson.data)
                        bundle.putString("seat_type", orderInfoGson.seat_type)
                        bundle.putString("coach_id", orderInfoGson.coach_id)
                        bundle.putString("seat_id", orderInfoGson.seat_id)
                        Looper.prepare()
                        val intent = Intent(context, OrderInfo::class.java).apply {
                            putExtras(bundle)
                        }
                        startActivity(intent)
                    }catch (e : Exception){
                        e.printStackTrace()
                    }


                    Looper.loop()
                }
            })
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

}