package com.example.snapup_android.viewSchedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.snapup_android.R
import com.example.snapup_android.R.id
import com.example.snapup_android.R.layout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.angmarch.views.NiceSpinner
import org.angmarch.views.OnSpinnerItemSelectedListener


class ViewScheduleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_schedule)
        setSupportActionBar(findViewById(R.id.toolbar))

        val scheduleList = ScheduleList.newInstance(5)

        findViewById<FloatingActionButton>(R.id.search_fab).setOnClickListener { view ->
            showCheckBoxDialog()
        }
    }
    private fun showCheckBoxDialog(){
        //把选项加入list
        val stationList: Array<String> = arrayOf("北京南站", "北京西站", "上海站", "天津站", "河北站", "新疆站", "成都站", "杭州站", "深圳站")      //输入途经站
        val timeList: Array<String> = arrayOf("1月1日", "1月3日", "1月8日", "1月15日", "2月5日", "2月7日")
        val seatList: Array<String> = arrayOf("一等座", "二等座")
        val view = LayoutInflater.from(this).inflate(R.layout.activity_search,null)
        val adapterStation = ArrayAdapter<String>(this, layout.activity_search, stationList)
        adapterStation.setDropDownViewResource(R.layout.activity_search)
        val adapterTime = ArrayAdapter<String>(this, layout.activity_search, timeList)
        adapterTime.setDropDownViewResource(R.layout.activity_search)
        val adapterSeat = ArrayAdapter<String>(this, layout.activity_search, seatList)
        adapterSeat.setDropDownViewResource(R.layout.activity_search)

        view.findViewById<NiceSpinner>(id.start_Station).setAdapter(adapterStation)
        view.findViewById<NiceSpinner>(id.start_Station).onSpinnerItemSelectedListener = OnSpinnerItemSelectedListener{ _: NiceSpinner, view: View, _: Int, _: Long ->
            fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem: String = parent.getItemAtPosition(position).toString()
                Toast.makeText(baseContext, selectedItem, Toast.LENGTH_SHORT).show()
            }
        }

        view.findViewById<NiceSpinner>(id.terminus).setAdapter(adapterStation)
        view.findViewById<NiceSpinner>(id.terminus).onSpinnerItemSelectedListener = OnSpinnerItemSelectedListener{ _: NiceSpinner, view: View, _: Int, _: Long ->
            fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem: String = parent.getItemAtPosition(position).toString()
                Toast.makeText(baseContext, selectedItem, Toast.LENGTH_SHORT).show()
            }
        }
        view.findViewById<NiceSpinner>(id.time).setAdapter(adapterTime)
        view.findViewById<NiceSpinner>(id.time).onSpinnerItemSelectedListener = OnSpinnerItemSelectedListener{ _: NiceSpinner, view: View, _: Int, _: Long ->
            fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem: String = parent.getItemAtPosition(position).toString()
                Toast.makeText(baseContext, selectedItem, Toast.LENGTH_SHORT).show()
            }
        }
        view.findViewById<NiceSpinner>(id.seat_kind).setAdapter(adapterSeat)
        view.findViewById<NiceSpinner>(id.seat_kind).onSpinnerItemSelectedListener = OnSpinnerItemSelectedListener{ _: NiceSpinner, view: View, _: Int, _: Long ->
            fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem: String = parent.getItemAtPosition(position).toString()
                Toast.makeText(baseContext, selectedItem, Toast.LENGTH_SHORT).show()
            }
        }
        try {
            AlertDialog.Builder(this)
                .setTitle("搜索车次")
                .setIcon(R.mipmap.ic_launcher_round)
                .setView(view)
                .setPositiveButton("提交"
                ) { dialog, id ->
                    // 搜索网络请求，如果为空，添加到
                    val empty = true
                    if(empty) Toast.makeText(this, "将当前搜索添加到代理抢票", Toast.LENGTH_SHORT).show()
                    else Toast.makeText(this,"跳转搜索",Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("取消"
                ) { dialog, id ->
                    // 取消
                    Toast.makeText(this,"取消",Toast.LENGTH_SHORT).show()
                }
                .create().show()
        }catch (e: Exception){
            e.printStackTrace()
            val a = 0
        }

    }


}