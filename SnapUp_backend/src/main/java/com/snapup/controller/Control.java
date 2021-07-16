package com.snapup.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mysql.cj.exceptions.StreamingNotifiable;
import com.snapup.pojo.*;
import com.snapup.service.*;
import com.snapup.util.TrainPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class Control {

    private Map<String, TrainPOJO> trainDict;

    @Autowired
    @Qualifier("restrictedUsrService")
    private RestrictedUsrService restrictedUsrService;

    @Autowired
    @Qualifier("trainRunServiceImpl")
    private TrainRunService trainRunService;

    @Autowired
    @Qualifier("stationOnLineServiceImpl")
    private StationOnLineService  stationOnLineService;

    @Autowired
    @Qualifier("timeTableService")
    private TimeTableService timeTableService;

    @Autowired
    @Qualifier("stationServiceImpl")
    private StationService stationService;

    @Autowired
    @Qualifier("trainSerialServiceImpl")
    private TrainSerialService trainSerialService;

    @Autowired
    @Qualifier("feedBackService")
    private FeedBackService feedBackService;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Autowired
    @Qualifier("ticketServiceImpl")
    private TicketService ticketService;

    @Autowired
    @Qualifier("orderService")
    private  OrderService orderService;

    @RequestMapping("/api/train/save-credit")
    @ResponseBody
    public JsonObject save_credit(@RequestBody JsonObject jo) {
        String identity = jo.get("identity").getAsString();
        String name = jo.get("name").getAsString();
        JsonObject res = new JsonObject();
        JsonObject result = new JsonObject();
        res.addProperty("message", "");
        res.addProperty("timestamp", "1");
        if (restrictedUsrService.findRestrictedUsr(identity) == null) {     /* 此征信限制人员不在数据库中 */
            restrictedUsrService.createRestrictedUsr(new RestrictedUsr(name, identity));
            result.addProperty("error", false);
            result.addProperty("reason", "成功添加条目");
        } else {                                                            /* 此征信限制人员在数据库中 */
            result.addProperty("error", true);
            result.addProperty("reason", "此人已在征信名单中");
        }
        res.add("result", result);
        res.addProperty("code", "");
        return res;
    }

    @RequestMapping("/api/train/delete-credit")
    @ResponseBody
    public JsonObject delete_credit(@RequestBody JsonArray ja) {
        int n = ja.size();                      /* 获得数组长度 */
        boolean all_delete_success = true;      /* 全部删除成功 */
        for (int i = 0; i < n; i++) {
            String del_id = ja.get(i).getAsString();
            if (restrictedUsrService.findRestrictedUsr(del_id) == null) {
                all_delete_success = false;
            } else {
                restrictedUsrService.deleteRestrictedUsr(new RestrictedUsr("mooc", del_id));
            }
        }
        JsonObject res = new JsonObject();
        JsonObject result = new JsonObject();
        res.addProperty("message", "");
        res.addProperty("timestamp", "1");
        if (all_delete_success) {
            result.addProperty("error", false);
            result.addProperty("reason","已经全部从征信名单中删除");
        } else {
            result.addProperty("error", true);
            result.addProperty("reason","有部分征信信息来源异常");
        }
        res.add("result", result);
        res.addProperty("code", "");
        return res;
    }

    @RequestMapping("/api/train/credit")
    @ResponseBody
    public JsonObject search_credit(
            @RequestParam(value="pageNo") int page_no,
            @RequestParam(value="pageSize") int page_size,
            @RequestParam(value="id", required = false) String identity,
            @RequestParam(value="name", required = false) String name
    ) {
        JsonObject res = new JsonObject();
        JsonObject result = new JsonObject();
        JsonArray data = new JsonArray();
        int status = 0;             /* 状态 0：无可选字段 (00)
                                     *     1：有可选字段身份证号 (01)
                                     *     2：有可选字段姓名 (10)
                                     *     3: 可选字段姓名、身份证号 (11)
                                     */
        if (identity != null && !identity.equals("")) status += 1;
        if (name != null && !name.equals("")) status += 1 << 1;

        List<RestrictedUsr> all_usr = restrictedUsrService.findAllRestrictedUsr();
        int n = all_usr.size();     /* 总数量 */
        int total_page = (n + page_size - 1) / page_size;   /* 页数上取整 */
        int st = (page_no - 1) * page_size + 1;     /* 开始条目 */
        int ed = page_no * page_size;               /* 终止条目 */
        int t = 0;                                  /* 满足搜索条件的个数 */


        switch (status) {
            case 0:
                for (int i = st; i <= ed && i <= n; i++) {
                    JsonObject temp = new JsonObject();
                    temp.addProperty("key", i);
                    temp.addProperty("id", i);
                    temp.addProperty("identity", all_usr.get(i - 1).getIdentity_id());
                    temp.addProperty("name", all_usr.get(i - 1).getName());
                    data.add(temp);
                }
                result.addProperty("pageSize", page_size);
                result.addProperty("pageNo", page_no);
                result.addProperty("totalCount", n);
                result.addProperty("totalPage", total_page);
                result.add("data", data);
                res.add("result", result);
                break;
            case 1:
                for (int i = 0; i < n; i++) {
                  if (all_usr.get(i).getIdentity_id().equals(identity)) {
                      JsonObject temp = new JsonObject();
                      t++;
                      if (t <= 10) {
                          temp.addProperty("key", t);
                          temp.addProperty("id", t);
                          temp.addProperty("identity", all_usr.get(i).getIdentity_id());
                          temp.addProperty("name", all_usr.get(i).getName());
                          data.add(temp);
                      }
                  }
                }
                result.addProperty("pageSize", page_size);
                result.addProperty("pageNo", page_no);
                result.addProperty("totalCount", t);
                total_page = (t + page_size - 1) / page_size;
                result.addProperty("totalPage", total_page);
                result.add("data", data);
                res.add("result", result);
                break;
            case 2:
                for (int i = 0; i < n; i++) {
                    if (all_usr.get(i).getName().equals(name)) {
                        JsonObject temp = new JsonObject();
                        t++;
                        if (t <= 10) {
                            temp.addProperty("key", t);
                            temp.addProperty("id", t);
                            temp.addProperty("identity", all_usr.get(i).getIdentity_id());
                            temp.addProperty("name", all_usr.get(i).getName());
                            data.add(temp);
                        }
                    }
                }
                result.addProperty("pageSize", page_size);
                result.addProperty("pageNo", page_no);
                result.addProperty("totalCount", t);
                total_page = (t + page_size - 1) / page_size;
                result.addProperty("totalPage", total_page);
                result.add("data", data);
                res.add("result", result);
                break;
            case 3:
                for (int i = 0; i < n; i++) {
                    if (all_usr.get(i).getName().equals(name) && all_usr.get(i).getIdentity_id().equals(identity)) {
                        JsonObject temp = new JsonObject();
                        t++;
                        if (t <= 10) {
                            temp.addProperty("key", t);
                            temp.addProperty("id", t);
                            temp.addProperty("identity", all_usr.get(i).getIdentity_id());
                            temp.addProperty("name", all_usr.get(i).getName());
                            data.add(temp);
                        }
                    }
                }
                result.addProperty("pageSize", page_size);
                result.addProperty("pageNo", page_no);
                result.addProperty("totalCount", t);
                total_page = (t + page_size - 1) / page_size;
                result.addProperty("totalPage", total_page);
                result.add("data", data);
                res.add("result", result);
                break;
        }
        return res;
    }

    @RequestMapping("/api/train/lines")
    @ResponseBody
    public JsonObject search_lines(
            @RequestParam(value="pageNo") int page_no,
            @RequestParam(value="pageSize") int page_size,
            @RequestParam(value="date") String show_date,
            @RequestParam(value="trainNo", required = false) String train_no,
            @RequestParam(value="trainType", required = false) Character train_type,
            @RequestParam(value="departStation", required = false) String depart_station,
            @RequestParam(value="arriveStation", required = false) String arrive_station,
            @RequestParam(value="departTime", required = false) String depart_time,
            @RequestParam(value="arriveTime", required = false) String arrive_time
    ) {

        JsonObject res = new JsonObject();
        JsonObject result = new JsonObject();
        JsonArray data = new JsonArray();

        DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
        Date show_day = null;
        try {
            show_day = fmt.parse(show_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        /* 标志量 */
        boolean train_no_flag = (train_no != null && !train_no.equals(""));
        boolean train_type_flag = (train_type != null);
        boolean depart_station_flag = (depart_station != null && !depart_station.equals(""));
        boolean arrive_station_flag = (arrive_station != null && !arrive_station.equals(""));
        boolean depart_time_flag = (depart_time != null && !depart_time.equals(""));
        boolean arrive_time_flag = (arrive_time != null && !arrive_time.equals(""));

        List<TrainRun> all_train = trainRunService.getAllTrainRun();
        int st = (page_no - 1) * page_size + 1;     /* 开始条目 */
        int ed = page_no * page_size;               /* 终止条目 */

        ArrayList<Set<String>> ass = new ArrayList<>();
        ArrayList<TrainRun> resultTrainList = new ArrayList<>();
        if (train_no_flag) {
            Set<String> set = new HashSet<>();
            set.add(train_no);
            ass.add(set);
        }
        if (depart_station_flag) {
            String depart_station_code = stationService.getStationByName(depart_station).getCode();
            List<String> as = stationOnLineService.getTrainLineByDepartStation(depart_station_code);
            Set<String> set = new HashSet<>(as);
            ass.add(set);
        }
        if (arrive_station_flag) {
            String arrive_station_code = stationService.getStationByName(arrive_station).getCode();
            List<String> as = stationOnLineService.getTrainLineByArriveStation(arrive_station_code);
            Set<String> set = new HashSet<>(as);
            ass.add(set);
        }
        if (depart_time_flag) {
            Time time = Time.valueOf(depart_time + ":00");
            List<String> as = stationOnLineService.getTrainLineByDepartTime(time);
            Set<String> set = new HashSet<>(as);
            ass.add(set);
        }
        if (arrive_time_flag) {
            Time time = Time.valueOf(arrive_time + ":00");
            List<String> as = stationOnLineService.getTrainLineByArriveTime(time);
            Set<String> set = new HashSet<>(as);
            ass.add(set);
        }

        for (TrainRun trainRun : all_train) {
            String trainRunCode = trainRun.getRun_code();
            boolean contain = true;
            for (Set<String> s : ass) {
                if (!s.contains(trainRunCode)) {
                    contain = false;
                    break;
                }
            }
            if (train_type_flag && trainRun.getType() != train_type) {

                contain = false;
            }
            if (!contain) {
                continue;
            }
            resultTrainList.add(trainRun);
        }

        int resultItemNums = resultTrainList.size();
        for (int i = 1; i <= resultItemNums; i++) {

            if (i >= st && i <= ed) {
                TrainRun trainRun = resultTrainList.get(i - 1);
                String trainRunCode = trainRun.getRun_code();
                int trainRunStationNum = trainRun.getStation_num();
                Station trainRunDepartStation = stationService.getStationByCode(stationOnLineService.getOneStation(trainRunCode, 1));
                Time trainRunDepartTime = timeTableService.getDepartTime(trainRunCode, trainRunDepartStation.getCode());
                Station trainRunArriveStation = stationService.getStationByCode(stationOnLineService.getOneStation(trainRunCode, trainRunStationNum));
                Time trainRunArriveTime = timeTableService.getArrivalTime(trainRunCode, trainRunArriveStation.getCode());
                String trainRunDepartTimeFormat = trainRunDepartTime.toString().substring(0, 5);
                String trainRunArriveTimeFormat = trainRunArriveTime.toString().substring(0, 5);

                JsonObject jot = new JsonObject();
                jot.addProperty("id", i);
                jot.addProperty("key", i);
                jot.addProperty("arriveTime", trainRunArriveTimeFormat);
                jot.addProperty("departTime", trainRunDepartTimeFormat);
                jot.addProperty("editable", false);
                jot.addProperty("stationInfo", trainRunDepartStation.getName() + " - " + trainRunArriveStation.getName());
                jot.addProperty("stationNum", trainRunStationNum);
                // 根据流水表车次安排，返回对应车次状态
                List<TrainSerial> trainSerials = trainSerialService.getCenterTrainSerial(trainRun.getRun_code());
                if (trainSerials != null) {
                    boolean status = false;
                    for (int j = 0; j < trainSerials.size(); j++) {
                        Date date = trainSerials.get(j).getDate();
                        if (show_day.equals(date)) {
                            status = true;
                        }
                    }
                    if (status) {       /* 今天安排该车次 */
                        jot.addProperty("status", 1);
                    } else {            /* 今天未安排该车次 */
                        jot.addProperty("status", 0);
                    }
                } else {                /* 今天未安排该车次 */
                    jot.addProperty("status", 0);
                }

                jot.addProperty("trainNo", trainRunCode);
                jot.addProperty("trainType", trainRun.getType());
                data.add(jot);
            }
        }

        int total_page = (resultItemNums + page_size - 1) / page_size;
        result.addProperty("pageSize", page_size);
        result.addProperty("pageNo", page_no);
        result.addProperty("totalCount", resultItemNums);
        result.addProperty("totalPage", total_page);
        result.add("data", data);
        res.add("result", result);
        return res;
    }


    @RequestMapping("/api/train/save-line")
    @ResponseBody
    public JsonObject save_line(@RequestBody JsonObject jo) {
        JsonObject res = new JsonObject();
        Boolean flag = jo.get("create").getAsBoolean();
        JsonObject jsonObject = jo.getAsJsonObject("lineInfo");
        String lineInfo = jsonObject.get("trainNo").getAsString();
        JsonArray ja = jo.get("lineStation").getAsJsonArray();
        char trainType;
        if (flag) {     /* 新建线路 */
            trainType = jsonObject.get("trainType").getAsString().charAt(0);
        } else {        /* 修改线路,不能修改车的类型 */
            trainType = trainRunService.getTrainType(lineInfo);
        }
        int is_legal = 0;       /*  0: 合法
                                 *  1: 站点名字不合法
                                 *  2: 录入时间不合法
                                 *  3: 新建线路存在
                                 *  4: 包含0个站点
                                 */
        //先判断所有数据是否合法
        if (flag) {
            List<TrainRun> all_train = trainRunService.getAllTrainRun();
            for (int i = 0; i < all_train.size(); i++) {
                if (all_train.get(i).getRun_code().equals(lineInfo)) {
                    is_legal = 3;
                }
                if (is_legal == 0 && ja.size() == 0) {
                    is_legal = 4;       /* 包含站点为0 */
                }
            }
        } else {
            if (is_legal == 0 && ja.size() == 0) {
                is_legal = 4;       /* 包含站点为0 */
            }
        }
        for (int i = 0; i < ja.size(); i++) {
            JsonObject tt = ja.get(i).getAsJsonObject();
            String station_name = tt.get("stationName").getAsString();  /* 车站名字 */
            String arrive_date_string = tt.get("arrive").getAsString(); /* 到达时间 */
            String depart_date_string = tt.get("depart").getAsString(); /* 出发时间 */
            Station station = stationService.getStationByName(station_name);
            if (station == null) {      /* 名字不合法 */
                is_legal = 1;
                break;
            }
            DateFormat sdf = new SimpleDateFormat("HH:mm");
            Date arrive_date = null;
            Date depart_date = null;
            try {
                sdf.setLenient(false);
                arrive_date = sdf.parse(arrive_date_string);
                arrive_date_string = sdf.format(arrive_date);
                depart_date = sdf.parse(depart_date_string);
                depart_date_string = sdf.format(depart_date);
            } catch (ParseException e) {
                is_legal = 2;       /* 时间不合法 */
                break;
            }
        }
        if (is_legal == 0) {
            if (!flag) {         /* 修改线路,将原有的信息删除，再添加 */    /* test pass */
                /* 必须将所有表同时删除，否则出错 */
                // 删除 station_on_line中满足run_code = lineInfo的表项
                stationOnLineService.delStation(lineInfo);
                // 删除 train_run 中满足 run_code = lineInfo的表项
                trainRunService.delLine(lineInfo);
                // 删除 time_table 中满足 run_code = lineInfo的表项
                timeTableService.delLine(lineInfo);
            }

            for (int i = 0; i < ja.size(); i++) {
                JsonObject tt = ja.get(i).getAsJsonObject();
                String station_name = tt.get("stationName").getAsString();  /* 车站名字 */
                String arrive_date_string = tt.get("arrive").getAsString(); /* 到达时间 */
                String depart_date_string = tt.get("depart").getAsString(); /* 出发时间 */
                Station station = stationService.getStationByName(station_name);
                DateFormat sdf = new SimpleDateFormat("HH:mm");
                Date arrive_date = null;
                Date depart_date = null;
                try {
                    arrive_date = sdf.parse(arrive_date_string);
                    arrive_date_string = sdf.format(arrive_date);
                    depart_date = sdf.parse(depart_date_string);
                    depart_date_string = sdf.format(depart_date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                timeTableService.addTime(lineInfo, station.getCode(), Time.valueOf(arrive_date_string + ":00"), Time.valueOf(depart_date_string + ":00"));
                // 插入time_table (lineInfo, station.getCode(), depart_date, arrive_date)
                stationOnLineService.addStation(lineInfo, i + 1, station.getCode());
                // 插入station_on_line (lineInfo, i + 1, station.getCode())
            }

            // 插入train_run (lineInfo, type?, ja.size(), 8, 540/700)
            if (trainType == 'G') {       /* 高铁 */
                trainRunService.createLine(lineInfo, trainType, ja.size(), 8, 700);
            } else {                    /* 动车 */
                trainRunService.createLine(lineInfo, trainType, ja.size(), 8, 540);
            }

            JsonObject result = new JsonObject();
            result.addProperty("error", false);
            result.addProperty("reason","成功添加");
            res.add("result", result);
        }
        else {      /* 数据不合法 */
            JsonObject result = new JsonObject();
            if (is_legal == 1) {
                result.addProperty("error", true);
                result.addProperty("reason","存在错误站点名称");
                res.add("result", result);
            } else if (is_legal == 2) {
                result.addProperty("error", true);
                result.addProperty("reason","录入时间不合法");
                res.add("result", result);
            } else if (is_legal == 3) {
                result.addProperty("error", true);
                result.addProperty("reason","输入线路存在");
                res.add("result", result);
            } else if (is_legal == 4) {
                result.addProperty("error", true);
                result.addProperty("reason","输入站点数量为零");
                res.add("result", result);
            }
        }

        return res;
    }

    @RequestMapping("/api/train/line-station")
    @ResponseBody
    public JsonObject search_line_station(
            @RequestParam(value="lineNo", required = false) String run_code
    ) {
        JsonObject jo = new JsonObject();
        jo.addProperty("message", "");
        jo.addProperty("timestamp", "1626065105209");
        JsonArray ja = new JsonArray();

        if (run_code != null && !run_code.equals("")) {
            List<TrainRun> all_train = trainRunService.getAllTrainRun();
            for (int i = 0; i < all_train.size(); i++) {
                if (run_code.equals(all_train.get(i).getRun_code())) {
                    List<Station> all_station  =  stationOnLineService.getAllStation(all_train.get(i).getRun_code());
                    for (int j = 0; j < all_station.size(); j++) {
                        JsonObject temp = new JsonObject();
                        String st_name = all_station.get(j).getName();
                        temp.addProperty("stationName", st_name);
                        Date st_date = timeTableService.getDepartTime(all_train.get(i).getRun_code(), stationService.getStationByName(st_name).getCode());
                        Date ed_date = timeTableService.getArrivalTime(all_train.get(i).getRun_code(), stationService.getStationByName(st_name).getCode());
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        temp.addProperty("arrive", sdf.format(st_date));
                        temp.addProperty("depart", sdf.format(ed_date));
                        ja.add(temp);
                    }
                    jo.add("result", ja);
                }
            }
        } else {
            jo.add("result", ja);
        }
        jo.addProperty("code", 0);
        return jo;
    }

    @RequestMapping("/api/train/arrange-line")
    @ResponseBody
    public JsonObject arrange_line(@RequestBody JsonObject jo) {
        JsonObject res = new JsonObject();
        String array_day_start = jo.get("date").getAsString();
        JsonArray ja = jo.get("lines").getAsJsonArray();
        int arrangeDay = jo.get("day").getAsInt();
        DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
        Date array_start_date = null;
        try {
            array_start_date = fmt.parse(array_day_start);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<String> run_code = new ArrayList<String>();
        for (int i = 0; i < ja.size(); i++) {
            String arrange_train = ja.get(i).getAsString();
            run_code.add(arrange_train);
        }
        trainSerialService.generateTrainSerial(run_code, arrangeDay, array_start_date);
        JsonObject result = new JsonObject();
        result.addProperty("error", false);
        result.addProperty("reason", "成功安排车次");
        List<TrainSerial> trainSerial = trainSerialService.getAllTrainSerial();
        for (int i = 0; i < trainSerial.size(); i++) {
            ticketService.initial(trainSerial.get(i).getSerial());
        }
        res.add("result", result);
        return res;
    }

    @RequestMapping("/api/train/feedback")
    @ResponseBody
    public JsonObject feed_back(
            @RequestParam(value="pageNo") int page_no,
            @RequestParam(value="pageSize") int page_size
    ) {
        JsonObject res = new JsonObject();
        JsonObject result = new JsonObject();
        JsonArray ja = new JsonArray();
        List<FeedBack> all_feed_back = feedBackService.getAllFeedBack();

        int n = all_feed_back.size();     /* 总数量 */
        int total_page = (n + page_size - 1) / page_size;   /* 页数上取整 */
        int st = (page_no - 1) * page_size + 1;     /* 开始条目 */
        int ed = page_no * page_size;               /* 终止条目 */

        for (int i = 1; i <= all_feed_back.size(); i++) {
            if (st <= i && i <= ed) {
                JsonObject temp = new JsonObject();
                String usr_name = all_feed_back.get(i - 1).getUsername();
                String tele = all_feed_back.get(i - 1).getTele();
                String comment = all_feed_back.get(i - 1).getComment();
                temp.addProperty("username", usr_name);
                temp.addProperty("tel", tele);
                temp.addProperty("detail", comment);
                ja.add(temp);
            }
        }
        result.addProperty("pageSize", page_size);
        result.addProperty("pageNo", page_no);
        result.addProperty("totalCount", n);
        result.addProperty("totalPage", total_page);
        result.add("data", ja);
        res.add("result", result);
        return res;
    }

    @RequestMapping("/api/train/getUsr")
    @ResponseBody
    public JsonObject get_usr(@RequestBody JsonObject jo) {
        JsonObject res = new JsonObject();
        String usr_name = jo.get("username").getAsString();
        String pwd = jo.get("pwd").getAsString();
        boolean flag = userService.isRegistered(usr_name);
        if (flag) {
            flag = userService.checkPassword(usr_name, pwd);
            if (flag) {     /* 用户名&密码正确 */
                User usr = userService.getUserInstance(usr_name, pwd);
                res.addProperty("usrname", usr.getUsername());
                res.addProperty("identity", usr.getIdentity_id());
                res.addProperty("gender", usr.getGender());
                res.addProperty("name", usr.getName());
                res.addProperty("tele", usr.getTele());
                res.addProperty("mail", usr.getMail());
                res.addProperty("pwd", usr.getPwd());
                res.addProperty("nickname", usr.getNickname());
            }  else {       /* 密码不正确 */
                res.addProperty("usrname", "-1");
                res.addProperty("identity", "");
                res.addProperty("gender", "");
                res.addProperty("name", "");
                res.addProperty("tele", "");
                res.addProperty("mail", "");
                res.addProperty("pwd", "");
                res.addProperty("nickname", "");
            }
        } else {        /* 不存在改用户 */
            res.addProperty("usrname", "-1");
            res.addProperty("identity", "");
            res.addProperty("gender", "");
            res.addProperty("name", "");
            res.addProperty("tele", "");
            res.addProperty("mail", "");
            res.addProperty("pwd", "");
            res.addProperty("nickname", "");
        }
        return res;
    }

    @RequestMapping("/api/train/registerUsr")
    @ResponseBody
    public JsonObject register_usr(@RequestBody JsonObject jo) {
        JsonObject res = new JsonObject();
        String usrname = jo.get("usrname").getAsString();
        JsonElement temp = jo.get("identity");
        String identity = temp == null ? null : temp.getAsString();
        temp = jo.get("name");
        String name = temp == null ? null : temp.getAsString();
        temp = jo.get("gender");
        char gender;
        if (temp != null && !temp.equals("")) {
            gender = temp.getAsString().charAt(0);
        } else {
            gender = 0;
        }

        temp = jo.get("tele");
        String tele = temp == null ? null : temp.getAsString();
        temp = jo.get("mail");
        String mail = temp == null ? null : temp.getAsString();
        temp = jo.get("pwd");
        String pwd = temp == null ? null : temp.getAsString();
        temp = jo.get("nickname");
        String nickname = temp == null ? null : temp.getAsString();
        Boolean is_success = pwd == null ? false : true;
        if (is_success)
            is_success = userService.registerUser(usrname, identity, gender, name, tele, mail, pwd, nickname);
        if (is_success) {      /* 成功注册 */
            res.addProperty("result", true);
        } else {               /* 注册失败 */
            res.addProperty("result", false);
        }
        return res;
    }

    @RequestMapping("/api/train/train-run-info")
    @ResponseBody
    public JsonArray train_info() {
        JsonArray res = new JsonArray();

        List<TrainRun> trainRuns = trainSerialService.getAllTrainRun();
        for (int i = 0; i < trainRuns.size(); i++) {
            TrainInfo trainInfo = trainRunService.getTrainInfo(trainRuns.get(i).getRun_code());
            JsonObject jo = new JsonObject();
            jo.addProperty("num_code", trainInfo.getNum_code());
            jo.addProperty("startTime", trainInfo.getStartTime().toString().substring(0, 5));
            jo.addProperty("endTime", trainInfo.getEndTime().toString().substring(0, 5));
            jo.addProperty("departName", trainInfo.getDepart_station_name());
            jo.addProperty("arrivalName", trainInfo.getArrival_station_name());
            res.add(jo);
        }

        return res;
    }

    @RequestMapping("/api/train/submit-order")
    @ResponseBody
    public JsonObject submit_order(@RequestBody JsonObject jo) {
        JsonObject res = new JsonObject();
        String run_code =  jo.get("run_code").getAsString();
        String dateStr = jo.get("date").getAsString();
        DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = fmt.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String depart_station_name = jo.get("depart_station_name").getAsString();
        String arrival_station_name = jo.get("arrival_station_name").getAsString();
        char seat_type = jo.get("seat_type").getAsString().charAt(0);
        String username = jo.get("username").getAsString();
        int run_serial = trainSerialService.findTrainSerial(new TrainSerial(0, date, run_code)).getSerial();
        float price = orderService.createOrder(run_serial, depart_station_name, arrival_station_name, seat_type, username);
        res.addProperty("price", price);
        return res;
    }

    @RequestMapping("/api/train/train-info-detail")
    @ResponseBody
    public JsonObject train_info_detail(@RequestBody JsonObject jo) {
        JsonObject res = new JsonObject();
        String run_code = jo.get("run_code").getAsString();
        String dateStr = jo.get("date").getAsString();
        String depart_station = jo.get("depart_station").getAsString();
        String arrive_station = jo.get("arrival_station").getAsString();
        List<Station> all_station = stationOnLineService.getAllStation(run_code);
        JsonArray ja = new JsonArray();
        for (int i = 0; i < all_station.size(); i++) {
            ja.add(all_station.get(i).getName());
        }
        res.addProperty("run_code", run_code);
        res.addProperty("date", dateStr);
        res.addProperty("depart_station", depart_station);
        res.addProperty("arrive_station", arrive_station);
        res.add("data", ja);

        return res;
    }

    @RequestMapping("/api/train/before-submit-order")
    @ResponseBody
    public JsonObject before_submit_order(@RequestBody JsonObject jo) {
        JsonObject res = new JsonObject();
        String run_code = jo.get("run_code").getAsString();
        List<Station> stations = stationOnLineService.getAllStation(run_code);
        List<TrainSerial> time_arrange = trainSerialService.getCenterTrainSerial(run_code);

        JsonArray ja = new JsonArray();
        JsonArray ja_time = new JsonArray();
        for (int i = 0; i < stations.size(); i++) {
            ja.add(stations.get(i).getName());
        }
        DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < time_arrange.size(); i++) {
            ja_time.add(fmt.format(time_arrange.get(i).getDate()));
        }
        res.add("depart", ja);
        res.add("arrive", ja);
        res.add("time", ja_time);
        return res;
    }

    @RequestMapping("/api/train/submit-feedback")
    @ResponseBody
    public JsonObject submit_feedback(@RequestBody JsonObject jo) {
        JsonObject res = new JsonObject();
        String username = jo.get("username").getAsString();
        String tele = jo.get("tele").getAsString();
        String comment = jo.get("comment").getAsString();

        feedBackService.createFeedBack(new FeedBack(1, username, tele, comment));
        res.addProperty("result", true);
        return res;
    }

    @RequestMapping("/api/train/person-info-change")
    @ResponseBody
    public JsonObject person_info_change(@RequestBody JsonObject jo) {
        JsonObject res = new JsonObject();
        String usrname = jo.get("usrname").getAsString();
        JsonElement temp = jo.get("identity");
        String identity = temp == null ? null : temp.getAsString();
        temp = jo.get("name");
        String name = temp == null ? null : temp.getAsString();
        temp = jo.get("gender");
        char gender;
        if (temp != null && !temp.equals("")) {
            gender = temp.getAsString().charAt(0);
        } else {
            gender = 0;
        }
        temp = jo.get("tele");
        String tele = temp == null ? null : temp.getAsString();
        temp = jo.get("mail");
        String mail = temp == null ? null : temp.getAsString();
        temp = jo.get("pwd");
        String pwd = temp == null ? null : temp.getAsString();
        temp = jo.get("nickname");
        String nickname = temp == null ? null : temp.getAsString();
        Boolean is_success = pwd == null ? false : true;
        if (is_success) {
            User user = new User(usrname, identity, gender, name, tele, mail, pwd, nickname);
            is_success = userService.updateUserInfo(user);
        }
        if (is_success) {      /* 成功注册 */
            res.addProperty("result", true);
        } else {               /* 注册失败 */
            res.addProperty("result", false);
        }
        return res;
    }

    @RequestMapping("/api/train/order-info")
    @ResponseBody
    public JsonArray order_info(@RequestBody JsonObject jo) {
        String username = jo.get("username").getAsString();
        List<Order> all_order = orderService.findAllUserOrder(username);
        JsonArray ja = new JsonArray();

        for (int i = 0; i < all_order.size(); i++) {
            JsonObject temp = new JsonObject();
            TrainInfo trainInfo = trainSerialService.getTrainInfo(all_order.get(i).getRun_serial());
            String run_code = trainInfo.getNum_code();
            temp.addProperty("run_code", run_code);
            String depart_code = stationOnLineService.getOneStation(run_code, all_order.get(i).getDepart_station_idx());
            String arrive_code =  stationOnLineService.getOneStation(run_code, all_order.get(i).getArrival_station_idx());
            temp.addProperty("departure_time", timeTableService.getDepartTime(run_code, depart_code).toString().substring(0, 5));
            temp.addProperty("arrival_time", timeTableService.getArrivalTime(run_code, arrive_code).toString().substring(0, 5));
            temp.addProperty("start_station_name", stationService.getStationByCode(depart_code).getName());
            temp.addProperty("end_station_name", stationService.getStationByCode(arrive_code).getName());
            temp.addProperty("order", all_order.get(i).getOrder_id());
            ja.add(temp);
        }
        return ja;
    }

    @RequestMapping("/api/train/order-info-detail")
    @ResponseBody
    public JsonObject order_info_detail(@RequestBody JsonObject jo) {
        JsonObject res = new JsonObject();
        int order_id = jo.get("order").getAsInt();
        String run_code = jo.get("run_code").getAsString();
        String depart_time = jo.get("depart_time").getAsString();
        String depart = jo.get("depart").getAsString();
        String arrive = jo.get("arrive").getAsString();
        res.addProperty("run_code", run_code);
        res.addProperty("depart_time", depart_time);
        res.addProperty("depart", depart);
        res.addProperty("arrive", arrive);

        JsonArray ja = new JsonArray();
        List<Station> all_station = stationOnLineService.getPassStation(run_code, depart, arrive);
        for (int i = 0; i < all_station.size(); i++) {
            ja.add(all_station.get(i).getName());
        }
        res.add("data",ja);
        res.addProperty("seat_type", orderService.findOrderById(order_id).getSeat_type());
        res.addProperty("coach_id", orderService.findOrderById(order_id).getCoach_id());
        res.addProperty("seat_id", orderService.findOrderById(order_id).getSeat_id());
        return res;
    }
}
