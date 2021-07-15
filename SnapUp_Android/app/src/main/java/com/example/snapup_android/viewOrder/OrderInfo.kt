package com.example.snapup_android.viewOrder

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.snapup_android.MyService
import com.example.snapup_android.R
import com.example.snapup_android.pojo.ValueAdded
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_order_info.*

class OrderInfo: AppCompatActivity() {
    //TO Add more fields about an order


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_info)

        val trainId = intent.getStringExtra("TRAIN_ID") ?: "ABC123"
        val startingStation = intent.getStringExtra("STARTING_STATION")?: "i am starting station"
        val terminus = intent.getStringExtra("TERMINUS")?: "i am terminus"
        val time = intent.getStringExtra("TIME")?: "i am time"
        val stopovers = intent.getStringArrayListExtra("STOPOVERS")//经停站
        val passengerName = intent.getStringExtra("NAME")?: "zqx"
        val seatKind = intent.getStringExtra("SEAT_KIND")?: "一等座"
        val state = intent.getStringExtra("STATE")?:"已支付"
        //val stopovers = intent.getStringArrayListExtra()


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        collapsingToolbar.title = trainId         //名字
        Glide.with(this).load(trainId).into(OrderImageView)

        TrainId.text = "车次号： $trainId"
        TrainBeginningStation.text = "始发站： $startingStation"
        TrainDestination.text = "终点站： $terminus"
        TrainTime.text = "发车时间： $time"
        PassengerName.text = "乘客姓名： $passengerName"
        if(stopovers!!.size <3) StationList.text = "经停站： $stopovers ..."
            else StationList.text = "经停站： $startingStation ... $terminus"
        Proxy.text = "状态： $state"
        SeatKind.text = "座椅类型： $seatKind"


        findViewById<FloatingActionButton>(R.id.order_info_fab).setOnClickListener { view ->
            showCheckBoxDialog()
        }
        findViewById<MaterialCardView>(R.id.order_Station).setOnClickListener {
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

    private fun showCheckBoxDialog(){
        val checkBoxItems: Array<String> = arrayOf("雨伞", "麦当劳",  "保险")

        val isCheck: BooleanArray = booleanArrayOf(false, false, false)


        AlertDialog.Builder(this)
            .setTitle("增值服务")
            .setIcon(R.mipmap.ic_launcher_round)
            .setMultiChoiceItems(checkBoxItems, isCheck) { dialog, which, isChecked ->
                if (isChecked){
                    Toast.makeText(this, checkBoxItems[which] + "  选中",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, checkBoxItems[which] + "  未选中", Toast.LENGTH_SHORT).show();
                }
            }
            .setPositiveButton("Confirm") {dialog, i ->
                var a = ValueAdded()
                a.isUmbrella = isCheck[0]
                a.isInsurance = isCheck[2]
                a.isMcd = isCheck[1]

                MyService.getValueAddedService().createValueAdded(a)
                dialog.dismiss()
        }.create().show()
    }



}