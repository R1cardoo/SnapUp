package com.example.snapup_android.viewOrder.content

import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object OrderContentList {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<OrderInfo> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    private val ITEM_MAP: MutableMap<String, OrderInfo> = HashMap()

    private const val COUNT = 25

    init {
        // 不要乱生成了，到时候可以注释init块，通过循环 addItem讲后端传过来的数据存到OrderContentList中
    }

    fun addOrderItem(item: OrderInfo) {
        ITEMS.add(item)
        ITEM_MAP[item.run_code] = item
    }

    fun clear(){
        ITEMS.clear()
    }


    /**
     * A dummy item representing a piece of content.
     */
    data class OrderInfo(val run_code: String, val start_station_name: String, val end_station_name: String,val departure_time: String, val arrival_time: String, val order: String) {

    }
}