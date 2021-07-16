package com.example.snapup_android.data.gsonClass

import android.os.Parcelable
import com.example.snapup_android.viewSchedule.content.ScheduleContentList.TrainInfo

data class UserGson(
    val usrname : String,
    val identity : String,
    val gender : String,  //'F' 'M'
    val name : String,
    val tele : String,
    val mail : String,
    val pwd : String,
    val nickname : String
)

data class RegisterGson( val result : Boolean)

data class ScheduleGson(
    val trainInfo : List<TrainInfo>
)

data class ScheduleInfoGson(
    val run_code : String,
    val date : String,
    val depart_station : String,
    val arrive_station : String,
    val data : List<String>
)

data class EditScheduleGson (
    val depart : List<String>,
    val arrive : List<String>,
    val time : List<String>
)

data class PriceGson(
    val price : Float
)

data class FeedbackGson(
    val result: Boolean
)