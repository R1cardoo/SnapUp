package com.example.snapup_android.viewOrder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.View.OnClickListener
import android.widget.TextView
import com.example.snapup_android.R

import com.example.snapup_android.viewOrder.content.OrderContentList.OrderInfo

/**
 * [RecyclerView.Adapter] that can display a [OrderInfo].
 * TODO: Replace the implementation with code for your data type.
 */
class MyOrderRecyclerViewAdapter(
    val values: List<OrderInfo>
) : RecyclerView.Adapter<MyOrderRecyclerViewAdapter.ViewHolder>() {

    private var mOnOrderClickListener: OnOrderClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_order_item, parent, false)
        return ViewHolder(view)
    }
    interface OnOrderClickListener {
        fun onItemClick(view: View?, position: Int)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.beginToDestination.text = "${item.start_station_name}——${item.end_station_name}"
        holder.time.text = "时间：${item.departure_time}——${item.arrival_time}"
        holder.trainId.text = "车次号：${item.run_code}"
        holder.proxyState.text = "订单号：${item.order}"
        if (mOnOrderClickListener != null) {
            holder.itemView.setOnClickListener(OnClickListener { view -> mOnOrderClickListener!!.onItemClick(view, position) })
        }
    }


    fun setOnOrderClickListener(OnOrderClickListener: OnOrderClickListener) {
        mOnOrderClickListener = OnOrderClickListener
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val beginToDestination: TextView = view.findViewById(R.id.BeginToDestination)
        val time: TextView = view.findViewById(R.id.Time)
        val trainId: TextView = view.findViewById(R.id.TrainId)
        val proxyState: TextView = view.findViewById(R.id.ProxyState)

        override fun toString(): String {
            return super.toString() + " '" + beginToDestination.text + "'"
        }
    }
}