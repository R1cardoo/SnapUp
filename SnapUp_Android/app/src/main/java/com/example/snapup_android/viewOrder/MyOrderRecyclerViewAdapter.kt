package com.example.snapup_android.viewOrder

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.snapup_android.R

import com.example.snapup_android.viewOrder.dummy.OrderList.OrderInfo

/**
 * [RecyclerView.Adapter] that can display a [OrderInfo].
 * TODO: Replace the implementation with code for your data type.
 */
class MyOrderRecyclerViewAdapter(
    val values: List<OrderInfo>
) : RecyclerView.Adapter<MyOrderRecyclerViewAdapter.ViewHolder>() {

    private var mOnOrderClickLitener: OnOrderClickLitener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.beginToDestination.text = "${item.BeginningStation}——${item.Destination}"
        holder.time.text = item.Time
        holder.trainId.text = item.TrainId
    }
    interface OnOrderClickLitener {
        fun onItemClick(view: View?, position: Int)
    }

    fun setOnOrderClickLitener(OnOrderClickLitener: OnOrderClickLitener) {
        mOnOrderClickLitener = OnOrderClickLitener
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val beginToDestination: TextView = view.findViewById(R.id.BeginToDestination)
        val time: TextView = view.findViewById(R.id.Time)
        val trainId: TextView = view.findViewById(R.id.TrainId)

        override fun toString(): String {
            return super.toString() + " '" + beginToDestination.text + "'"
        }
    }
}