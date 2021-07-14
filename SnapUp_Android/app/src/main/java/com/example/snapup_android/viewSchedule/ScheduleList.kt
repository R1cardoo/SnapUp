package com.example.snapup_android.viewSchedule

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.snapup_android.MyService
import com.example.snapup_android.R
import com.example.snapup_android.viewSchedule.MyScheduleRecyclerViewAdapter.OnItemClickListener
import com.example.snapup_android.viewSchedule.content.ScheduleContentList
import java.util.ArrayList


/**
 * A fragment representing a list of Items.
 */
class ScheduleList : Fragment() {

    private var columnCount = 1

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
                        //点击事件 弹出详情请求详细信息 runcode是 ScheduleContentList里的一条数据
                        val runCode = "1"
                        MyService.trainInfo = MyService.getTrainRunService().getTrainInfo(runCode)

                        val stopovers = ArrayList<String>()
                        stopovers.add("sample")
                        val bundle = Bundle()
                        //向bundle填入方法返回的信息
                        bundle.putString("TRAIN_ID","a station id" )
                        bundle.putString("STARTING_STATION", "a starting station" )
                        bundle.putString("TERMINUS", "a terminus")
                        bundle.putString("TIME", "a time")
                        bundle.putStringArrayList("STOPOVERS", stopovers)

                        val intent = Intent(context, ScheduleInfo::class.java).apply {
                            putExtras(bundle)
                        }
                        startActivity(intent)
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
}