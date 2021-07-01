package com.example.snapup_android.viewInfo

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.snapup_android.R
import com.example.snapup_android.viewInfo.dummy.DummyContent.DummyItem


/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(
    val values: List<DummyItem>
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id
        holder.contentView.text = item.content
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(OnClickListener { view -> mOnItemClickLitener!!.onItemClick(view, position) })
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {    //添加其他字段
        val idView: TextView = view.findViewById(R.id.item_number)
        val contentView: TextView = view.findViewById(R.id.content)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}