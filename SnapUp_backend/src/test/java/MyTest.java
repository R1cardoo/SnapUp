import com.google.gson.JsonObject;
import com.snapup.controller.Control;
import com.snapup.pojo.RestrictedUsr;
import com.snapup.service.*;
import com.snapup.controller.*;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

public class MyTest {
    @Test
    public void TestUser(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserServiceImpl userService = (UserServiceImpl) ctx.getBean("userServiceImpl");
        System.out.println(userService.isRegistered("rex"));
        userService.registerUser("rex", "110102199909223017", 'M', "王睿浦",
                "15201657270", "rexwoodrp@gmail.com", "123456", "wrp");
        System.out.println(userService.isRegistered("rex"));
        System.out.println(userService.checkPassword("rex", "123456"));
        System.out.println(userService.getUserInstance("rex", "123456").getIdentity_id());

        System.out.println(userService.getPwd("rex"));
    }
    @Test
    public void TestStation(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        StationServiceImpl stationService = (StationServiceImpl) ctx.getBean("stationServiceImpl");
        System.out.println(stationService.getStationByName("北京"));
    }
    @Test
    public void TestTrainLine(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        StationOnLineServiceImpl stationOnLineService = (StationOnLineServiceImpl)ctx.getBean("stationOnLineServiceImpl");
        //输入始发站和目的站，返回线路
        System.out.println(stationOnLineService.getTrainLine("18", "2492"));
        //输入始发站和目的站，返回所有满足该旅程的线路，包括中间站上下车
        System.out.println(stationOnLineService.getTrainLine2("18", "2492"));

        System.out.println(stationOnLineService.getAllStation("c1001"));
    }
    @Test
    public void TestTrainSerial(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        TrainSerialServiceImpl trainSerialService = (TrainSerialServiceImpl)ctx.getBean("trainSerialServiceImpl");
        Date date = new Date(2021-1900, 9, 10);
        System.out.println(date);
        trainSerialService.createTrainSerial(date, "c1001");

    }
    @Test
    public void TestTrainRun(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        TrainRunServiceImpl trainRunService = (TrainRunServiceImpl)ctx.getBean("trainRunServiceImpl");
        System.out.println("c1001的火车类型："+trainRunService.getTrainType("c1001"));
        System.out.println("c1001的车箱数："+trainRunService.getCoachNum("c1001"));
        System.out.println("c1001的座位数："+trainRunService.getSeatNum("c1001"));
        System.out.println("c1001的线路站点数："+trainRunService.getStationNum("c1001"));
    }
    @Test
    public void TestSeat(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        SeatServiceImpl seatService = (SeatServiceImpl) ctx.getBean("seatServiceImpl");
        seatService.createTrainSerialSeat(new Date(2021-1900, 10-1, 10), "c1001");
    }
    @Test
    public void Test_usr(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        Usr_train_search_impl usr_train_search = (Usr_train_search_impl) ctx.getBean("usr_train_search_impl");
        System.out.println(usr_train_search.search_detail("长春", "延吉西"));
    }
    @Test
    public void TestTicketService(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        TicketServiceImpl ticketService = (TicketServiceImpl) ctx.getBean("ticketServiceImpl");
        ticketService.initial(1);
        // ticketService.createTicket(1, "18", "2492");
        //ticketService.addTicket(1, "18", "2492", '1');
        //ticketService.subTicket(1, "18", "2492", '1');
        //ticketService.subTicket(1, "18", "2492", '2');


    }
    @Test
    public void TestResUsr(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        RestrictedUsrServiceImpl restrictedUsrService = (RestrictedUsrServiceImpl) ctx.getBean("restrictedUsrService");
        restrictedUsrService.createRestrictedUsr(new RestrictedUsr("王睿浦", "1111111"));
    }
    @Test
    public void TestPassenger(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        PassengerServiceImpl passengerService = (PassengerServiceImpl) ctx.getBean("passengerService");
        passengerService.createPassenger("rex", 'A');
        passengerService.createPassenger("rexwrp", 'A');
        passengerService.updatePassengerType("rex", 'B');
    }
    @Test
    public void TestRestrictedUsr() {
        Control c = new Control();
        JsonObject object = new JsonObject();
        object.addProperty("identity", "110105199905140634");
        object.addProperty("name", "张三");
        object.addProperty("id", 0);
        //System.out.println(c.save_credit(object));
    }
}
