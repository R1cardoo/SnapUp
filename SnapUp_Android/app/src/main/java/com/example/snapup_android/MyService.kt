package com.example.snapup_android

import com.example.snapup_android.pojo.Order
import com.example.snapup_android.pojo.TrainInfo
import com.example.snapup_android.pojo.User
import com.example.snapup_android.pojo.ValueAdded
import com.example.snapup_android.service.FeedBackService
import com.example.snapup_android.service.FeedBackServiceImpl
import com.example.snapup_android.service.OrderService
import com.example.snapup_android.service.OrderServiceImpl
import com.example.snapup_android.service.PassengerService
import com.example.snapup_android.service.PassengerServiceImpl
import com.example.snapup_android.service.SeatService
import com.example.snapup_android.service.SeatServiceImpl
import com.example.snapup_android.service.StationOnLineService
import com.example.snapup_android.service.StationOnLineServiceImpl
import com.example.snapup_android.service.StationService
import com.example.snapup_android.service.StationServiceImpl
import com.example.snapup_android.service.TrainRunService
import com.example.snapup_android.service.TrainRunServiceImpl
import com.example.snapup_android.service.TrainSerialService
import com.example.snapup_android.service.TrainSerialServiceImpl
import com.example.snapup_android.service.UserService
import com.example.snapup_android.service.UserServiceImpl
import com.example.snapup_android.service.Usr_train_search
import com.example.snapup_android.service.Usr_train_search_impl
import com.example.snapup_android.service.ValueAddedService
import com.example.snapup_android.service.ValueAddedServiceImpl

object MyService {
    val a = UserServiceImpl()
    val b = PassengerServiceImpl()
    val c = OrderServiceImpl()
    val d = SeatServiceImpl()
    val e = Usr_train_search_impl()
    val f = TrainRunServiceImpl()
    val g = TrainSerialServiceImpl()
    val h = ValueAddedServiceImpl()
    val i = FeedBackServiceImpl()
    val j = StationOnLineServiceImpl()
    val k = StationServiceImpl()

    var user = User()
    var trainInfo = TrainInfo()
    var valueAdded = ValueAdded()

    lateinit var orderList : List<Order>
    init{

    }
    fun getUserService(): UserService = a

    fun getPassengerService() : PassengerService = b

    fun getOrderService() : OrderService = c

    fun getSeatService() : SeatService = d

    fun getUserTrainSearchService(): Usr_train_search_impl = e

    fun getTrainRunService(): TrainRunService = f

    fun getTrainSerialService(): TrainSerialService = g

    fun getValueAddedService(): ValueAddedService = h

    fun getFeedbackService(): FeedBackService = i

    fun getStationOnLineService(): StationOnLineService = j

    fun getStationService(): StationService = k








}