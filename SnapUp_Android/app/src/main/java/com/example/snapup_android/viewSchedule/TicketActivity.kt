package com.example.snapup_android.viewSchedule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.snapup_android.MyService
import com.example.snapup_android.R
import com.example.snapup_android.R.id
import com.example.snapup_android.R.layout
import org.angmarch.views.NiceSpinner
import org.angmarch.views.OnSpinnerItemSelectedListener

class TicketActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)

        val stationList: ArrayList<String> = MyService.stationList    //输入途经站
        val timeList: ArrayList<String> = arrayOf("7月17日","7月18日", "7月19日", "7月20日","7月21日", "7月22日") as ArrayList<String>
        val seatList: ArrayList<String> = arrayOf("一等座", "二等座") as ArrayList<String>

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
            val builder = AlertDialog.Builder(this)
            builder.setTitle("支付")
                .setMessage("价格是")    //补充价格
                .setPositiveButton("提交"
                ) { dialog, id ->
                    // 支付网络请求
                    Toast.makeText(this,"支付成功",Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("取消"
                ) { dialog, id ->
                    // 取消
                    Toast.makeText(this,"取消支付",Toast.LENGTH_SHORT).show()
                }
                .create().show()
        }

    }
}