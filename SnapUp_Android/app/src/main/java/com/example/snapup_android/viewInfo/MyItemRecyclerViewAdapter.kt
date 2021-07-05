package com.example.snapup_android.viewInfo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.snapup_android.R
import com.example.snapup_android.viewInfo.dummy.TrainList.TrainInfo


/**
 * [RecyclerView.Adapter] that can display a [TrainInfo].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(
    val values: List<TrainInfo>
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    private var mOnItemClickLitener: OnItemClickLitener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_schedule_list, parent, false)
        return ViewHolder(view)
    }
    interface OnItemClickLitener {
        fun onItemClick(view: View?, position: Int)
    }

    fun setOnItemClickLitener(OnItemClickLitener: OnItemClickLitener) {
        mOnItemClickLitener = OnItemClickLitener
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.beginToDestination.text = "${item.BeginningStation}——${item.Destination}"
        holder.time.text = item.Time
        holder.trainId.text = item.TrainId
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(OnClickListener { view -> mOnItemClickLitener!!.onItemClick(view, position) })
        }
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