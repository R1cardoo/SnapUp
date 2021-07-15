package com.example.snapup_android

import com.example.snapup_android.pojo.Order
import com.example.snapup_android.pojo.TrainInfo
import com.example.snapup_android.pojo.User
import com.example.snapup_android.pojo.ValueAdded
import com.example.snapup_android.service.*
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext

object MyService {
    var ctx: ApplicationContext = ClassPathXmlApplicationContext("applicationContext.xml")
    var userService = ctx.getBean("userServiceImpl") as UserServiceImpl



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
    lateinit var stationList : ArrayList<String>
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