package com.example.snapup_android.viewSchedule

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.snapup_android.R
import com.example.snapup_android.R.id
import com.example.snapup_android.R.layout
import org.angmarch.views.NiceSpinner
import org.angmarch.views.OnSpinnerItemSelectedListener

class TicketActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)

        val stationList: Array<String> = arrayOf("北京市", "东城区",  "西城区","朝阳区","海淀区","通州区","北京工业大学","11号楼","A406")      //输入途经站

        val adapter = ArrayAdapter<String>(this, layout.activity_ticket, stationList)
        adapter.setDropDownViewResource(R.layout.activity_ticket)

        findViewById<NiceSpinner>(id.start_Station).setAdapter(adapter)
        findViewById<NiceSpinner>(id.start_Station).onSpinnerItemSelectedListener = OnSpinnerItemSelectedListener{ _: NiceSpinner, view: View, _: Int, _: Long ->
            fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem: String = parent.getItemAtPosition(position).toString()
                Toast.makeText(baseContext, selectedItem, Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<NiceSpinner>(id.terminus).setAdapter(adapter)
        findViewById<NiceSpinner>(id.terminus).onSpinnerItemSelectedListener = OnSpinnerItemSelectedListener{ _: NiceSpinner, view: View, _: Int, _: Long ->
            fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem: String = parent.getItemAtPosition(position).toString()
                Toast.makeText(baseContext, selectedItem, Toast.LENGTH_SHORT).show()
            }
        }
        findViewById<NiceSpinner>(id.time).setAdapter(adapter)
        findViewById<NiceSpinner>(id.time).onSpinnerItemSelectedListener = OnSpinnerItemSelectedListener{ _: NiceSpinner, view: View, _: Int, _: Long ->
            fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem: String = parent.getItemAtPosition(position).toString()
                Toast.makeText(baseContext, selectedItem, Toast.LENGTH_SHORT).show()
            }
        }
        findViewById<NiceSpinner>(id.seat_kind).setAdapter(adapter)
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
                .setPositiveButton("提交",
                    DialogInterface.OnClickListener { dialog, id ->
                        // 支付网络请求
                        Toast.makeText(this,"支付成功",Toast.LENGTH_SHORT).show()
                    })
                .setNegativeButton("取消",
                    DialogInterface.OnClickListener { dialog, id ->
                        // 取消
                        Toast.makeText(this,"取消支付",Toast.LENGTH_SHORT).show()
                    })
            // Create the AlertDialog object and return it
            builder.create().show()
        }

    }
}