package com.example.snapup_android.viewSchedule

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.snapup_android.R
import com.example.snapup_android.R.id
import com.example.snapup_android.R.id.buy
import com.example.snapup_android.R.id.start_Station
import com.example.snapup_android.R.id.terminus
import com.example.snapup_android.R.id.time
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner
import kotlinx.android.synthetic.main.activity_order_info.TrainBeginningStation
import kotlinx.android.synthetic.main.activity_order_info.TrainDestination
import kotlinx.android.synthetic.main.activity_order_info.TrainId
import kotlinx.android.synthetic.main.activity_order_info.TrainTime
import kotlinx.android.synthetic.main.activity_schedule_info.TrainImageView
import kotlinx.android.synthetic.main.activity_schedule_info.collapsingToolbar
import kotlinx.android.synthetic.main.activity_search.terminus
import org.angmarch.views.NiceSpinner
import org.angmarch.views.OnSpinnerItemSelectedListener
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


        findViewById<FloatingActionButton>(R.id.search_fab).setOnClickListener { view ->
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
        val stationList: Array<String> = arrayOf("北京市", "东城区",  "西城区","朝阳区","海淀区","通州区","北京工业大学","11号楼","A406")      //输入途经站

        val view = LayoutInflater.from(this).inflate(R.layout.activity_search,null)
        val adapter = ArrayAdapter<String>(this, R.layout.activity_search, stationList)
        adapter.setDropDownViewResource(R.layout.activity_search)

        view.findViewById<NiceSpinner>(start_Station).setAdapter(adapter)
        view.findViewById<NiceSpinner>(start_Station).onSpinnerItemSelectedListener = OnSpinnerItemSelectedListener{ _: NiceSpinner, view: View, _: Int, _: Long ->
            fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem: String = parent.getItemAtPosition(position).toString()
                Toast.makeText(baseContext, selectedItem, Toast.LENGTH_SHORT).show()
            }
        }

        view.findViewById<NiceSpinner>(id.terminus).setAdapter(adapter)
        view.findViewById<NiceSpinner>(id.terminus).onSpinnerItemSelectedListener = OnSpinnerItemSelectedListener{ _: NiceSpinner, view: View, _: Int, _: Long ->
            fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem: String = parent.getItemAtPosition(position).toString()
                Toast.makeText(baseContext, selectedItem, Toast.LENGTH_SHORT).show()
            }
        }
        view.findViewById<NiceSpinner>(time).setAdapter(adapter)
        view.findViewById<NiceSpinner>(time).onSpinnerItemSelectedListener = OnSpinnerItemSelectedListener{ _: NiceSpinner, view: View, _: Int, _: Long ->
            fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem: String = parent.getItemAtPosition(position).toString()
                Toast.makeText(baseContext, selectedItem, Toast.LENGTH_SHORT).show()
            }
        }
        view.findViewById<NiceSpinner>(id.seat_kind).setAdapter(adapter)
        view.findViewById<NiceSpinner>(id.seat_kind).onSpinnerItemSelectedListener = OnSpinnerItemSelectedListener{ _: NiceSpinner, view: View, _: Int, _: Long ->
            fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem: String = parent.getItemAtPosition(position).toString()
                Toast.makeText(baseContext, selectedItem, Toast.LENGTH_SHORT).show()
            }
        }
        view.findViewById<Button>(buy).setOnClickListener {
            //支付
        }
        try {
            AlertDialog.Builder(this)
                .setTitle("搜索车次")
                .setIcon(R.mipmap.ic_launcher_round)
                .setView(view)
                .create().show()
        }catch (e: Exception){
            e.printStackTrace()
            val a = 0
        }

    }



}

