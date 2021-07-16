package com.example.snapup_android.viewSchedule

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.snapup_android.Http
import com.example.snapup_android.R
import com.example.snapup_android.data.gsonClass.ScheduleInfoGson
import com.example.snapup_android.TrainInfor
import com.example.snapup_android.viewSchedule.MyScheduleRecyclerViewAdapter.OnItemClickListener
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
class ScheduleList : Fragment() {

    private var columnCount = 1

    var stopovers = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_schedule_item_list, container, false)
        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyScheduleRecyclerViewAdapter(ScheduleContentList.ITEMS) //在这里init ScheduleList
                (adapter as MyScheduleRecyclerViewAdapter).setOnItemClickListener(object : OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        //点击事件 弹出详情请求详细信息
                        requestScheduleInfo(position)

                    }
                })
                view.adapter = adapter
            }
        }

        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ScheduleList().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }

    fun requestScheduleInfo(position: Int) {
        val mime = "application/json;charset=utf-8".toMediaTypeOrNull()

        val json = JSONObject()
            .put("run_code", ScheduleContentList.ITEMS[position].num_code)
            .put("date", ScheduleContentList.ITEMS[position].startTime)
            .put("depart_station", ScheduleContentList.ITEMS[position].departName)
            .put("arrival_station", ScheduleContentList.ITEMS[position].arrivalName).toString()
        try {
            val request = Request.Builder()//创建Request 对象。
                .url("${Http.prefix}/api/train/train-info-detail/")
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
                    val scheduleInfoGson = Gson().fromJson(response.body?.string(), ScheduleInfoGson::class.java)
                    Log.d("UPDATE", "OnResponse: $scheduleInfoGson")
                    stopovers = scheduleInfoGson.data as ArrayList<String>
                }catch (e : Exception){
                    e.printStackTrace()
                }

                TrainInfor.trainId = ScheduleContentList.ITEMS[position].num_code
                val bundle = Bundle()
                //向bundle填入方法返回的信息
                bundle.putString("TRAIN_ID",ScheduleContentList.ITEMS[position].num_code )
                bundle.putString("STARTING_STATION", ScheduleContentList.ITEMS[position].departName)
                bundle.putString("TERMINUS", ScheduleContentList.ITEMS[position].arrivalName)
                bundle.putString("TIME", ScheduleContentList.ITEMS[position].startTime)
                bundle.putStringArrayList("STOPOVERS", stopovers as ArrayList<String>)
                Looper.prepare()
                val intent = Intent(context, ScheduleInfo::class.java).apply {
                    putExtras(bundle)
                }
                startActivity(intent)
                Looper.loop()
            }
        })
        }catch (e : Exception){
            e.printStackTrace()
        }
    }
}