package com.example.snapup_android.viewOrder

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.snapup_android.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_order_info.*
import java.util.ArrayList

class OrderInfo: AppCompatActivity() {
    //TO Add more fields about an order
    private val TRAIN_ID = ""
    private val STARTING_STATION = ""
    private val TERMINUS = ""
    private val TIME = ""
    private val NAME = ""
    val STOPOVERS : List<String> = ArrayList() //经停站

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_info)

        val trainId = intent.getStringExtra(TRAIN_ID) ?: "ABC123"
        val startingStation = intent.getStringExtra(STARTING_STATION)?: "i am starting station"
        val terminus = intent.getStringExtra(TERMINUS)?: "i am terminus"
        val time = intent.getStringExtra(TIME)?: "i am time"
        val passengerName = intent.getStringExtra(NAME)?: "zqx"
        //val stopovers = intent.getStringArrayListExtra()


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        collapsingToolbar.title = trainId         //名字
        Glide.with(this).load(trainId).into(OrderImageView)

        TrainId.text = "车次号： $trainId"
        TrainBeginningStation.text = "始发站：$startingStation"
        TrainDestination.text = "终点站：$terminus"
        TrainTime.text = "发车时间： $time"
        PassengerName.text = "乘客姓名： $passengerName"


        findViewById<FloatingActionButton>(R.id.order_info_fab).setOnClickListener { view ->
            showCheckBoxDialog()
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
            .setPositiveButton("good") {dialog, i ->
                dialog.dismiss()
        }.create().show()
    }



}