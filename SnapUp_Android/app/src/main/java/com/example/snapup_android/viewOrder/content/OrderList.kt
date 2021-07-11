package com.example.snapup_android.viewOrder.content

import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object OrderList {

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
        // Add some sample items.
        for (i in 1..OrderList.COUNT) {
            addItem(createDummyItem(i))
        }
    }

    private fun addItem(item: OrderInfo) {
        OrderList.ITEMS.add(item)
        OrderList.ITEM_MAP[item.TrainId] = item
    }

    private fun createDummyItem(position: Int): OrderInfo {
        return OrderInfo("ABC$position", "ItemBegin $position", "ItemDestination $position","1：00——2：00", "代理抢票中")
    }


    /**
     * A dummy item representing a piece of content.
     */
    data class OrderInfo(val TrainId: String, val BeginningStation: String, val Destination: String,val Time: String, val state: String) {
        override fun toString(): String = "$BeginningStation——$Destination"
    }
}