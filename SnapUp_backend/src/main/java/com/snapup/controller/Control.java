package com.snapup.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mysql.cj.exceptions.StreamingNotifiable;
import com.snapup.pojo.RestrictedUsr;
import com.snapup.pojo.Station;
import com.snapup.pojo.TrainRun;
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
                jot.addProperty("status", 1);
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
            //TODO: 检查合不合法
            timeTableService.addTime(lineInfo, station.getCode(), Time.valueOf(arrive_date_string + ":00"), Time.valueOf(depart_date_string + ":00"));
            // 插入time_table (lineInfo, station.getCode(), depart_date, arrive_date)
            stationOnLineService.addStation(lineInfo, i + 1, station.getCode());
            // 插入station_on_line (lineInfo, i + 1, station.getCode())
        }
        //TODO: 插入train_run (lineInfo, type?, ja.size(), 8, 540/700)
        trainRunService.createLine(lineInfo, 'G', ja.size(), 8, 700);
        JsonObject result = new JsonObject();
        result.addProperty("error", false);
        result.addProperty("reason","成功添加");
        res.add("result", result);
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
}
