package com.example.snapup_android.viewSchedule.content

import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object ScheduleContentList {

    /**
     * An array
     * of sample (dummy) items.
     */
    val ITEMS: MutableList<TrainInfo> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    private val ITEM_MAP: MutableMap<String, TrainInfo> = HashMap()

    private const val COUNT = 25

    init {
        // 不要乱生成了，到时候可以注释init块，通过循环 addItem讲后端传过来的数据存到ScheduleContentList中
        for (i in 1..COUNT) {
            addItem(createScheduleItem(i))
        }
    }

    private fun addItem(item: TrainInfo) {
        ITEMS.add(item)
        ITEM_MAP[item.TrainId] = item
    }

    private fun createScheduleItem(position: Int): TrainInfo {
        return TrainInfo("ABC$position", "ItemBegin $position", "ItemDestination $position","1：00——2：00")
    }


    /**
     * A dummy item representing a piece of content.
     */
    data class TrainInfo(val TrainId: String, val BeginningStation: String, val Destination: String,val Time: String) {
        override fun toString(): String = "$BeginningStation——$Destination"
    }
}