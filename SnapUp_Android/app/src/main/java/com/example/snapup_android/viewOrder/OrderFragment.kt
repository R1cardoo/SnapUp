package com.example.snapup_android.viewOrder

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.snapup_android.R
import com.example.snapup_android.viewInfo.MyItemRecyclerViewAdapter
import com.example.snapup_android.viewInfo.MyItemRecyclerViewAdapter.OnItemClickLitener
import com.example.snapup_android.viewInfo.TrainInfo
import com.example.snapup_android.viewOrder.MyOrderRecyclerViewAdapter.OnOrderClickLitener
import com.example.snapup_android.viewOrder.dummy.DummyContent

/**
 * A fragment representing a list of Items.
 */
class OrderFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyOrderRecyclerViewAdapter(DummyContent.ITEMS)
                (adapter as MyOrderRecyclerViewAdapter).setOnOrderClickLitener(object : OnOrderClickLitener {
                    override fun onItemClick(view: View?, position: Int) {
                        //点击事件 弹出详情
                        val dummy = (adapter as MyOrderRecyclerViewAdapter).values[position]

                        val bundle = Bundle()
                        //向bundle传递需要的字段

                        val intent = Intent(context, OrderInfo::class.java).apply {
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
            OrderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}