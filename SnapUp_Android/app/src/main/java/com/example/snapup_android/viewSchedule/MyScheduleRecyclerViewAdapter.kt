package com.example.snapup_android.viewSchedule

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.snapup_android.R
import com.example.snapup_android.viewSchedule.content.ScheduleContentList.TrainInfo


/**
 * [RecyclerView.Adapter] that can display a [TrainInfo].
 * TODO: Replace the implementation with code for your data type.
 */
class MyScheduleRecyclerViewAdapter(
    val values: List<TrainInfo>
) : RecyclerView.Adapter<MyScheduleRecyclerViewAdapter.ViewHolder>() {

    private var mOnItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_schedule_item, parent, false)
        return ViewHolder(view)
    }
    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.beginToDestination.text = "${item.BeginningStation}——${item.Destination}"
        holder.time.text = item.Time
        holder.trainId.text = item.TrainId
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(OnClickListener { view -> mOnItemClickListener!!.onItemClick(view, position) })
        }
    }

    fun setOnItemClickListener(OnItemClickListener: OnItemClickListener) {
        mOnItemClickListener = OnItemClickListener
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {    //添加其他字段
        val beginToDestination: TextView = view.findViewById(R.id.BeginToDestination)
        val time: TextView = view.findViewById(R.id.Time)
        val trainId: TextView = view.findViewById(R.id.TrainId)

        override fun toString(): String {
            return super.toString() + " '" + beginToDestination.text + "'"
        }
    }
}