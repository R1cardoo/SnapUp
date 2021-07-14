package com.snapup.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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

//        if (trainDict == null) {
//            File dictFile = new File("./dict.dat");
//            if (dictFile.exists()) {
//                try {
//                    ObjectInputStream trainDictStream = new ObjectInputStream(new FileInputStream(dictFile));
//                    trainDict = (Map<String, TrainPOJO>) trainDictStream.readObject();
//                    trainDictStream.close();
//                } catch (IOException | ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                trainDict = new HashMap<>();
//                List<TrainRun> all_train = trainRunService.getAllTrainRun();
//                for (TrainRun trainRun : all_train) {
//                    String trainRunCode = trainRun.getRun_code();
//                    Character trainRunType = trainRun.getType();
//                    int allN = trainRun.getStation_num();
//                    String trainRunDepartName = stationService.getStationByCode(stationOnLineService.getOneStation(trainRunCode, 1)).getName();
//                    String trainRunArriveName = stationService.getStationByCode(stationOnLineService.getOneStation(trainRunCode, 1)).getName();
//                    Date trainRunDepartTime = timeTableService.getDepartTime(trainRunCode, trainRunDepartName);
//                    Date trainRunArriveTime = timeTableService.getArrivalTime(trainRunCode, trainRunArriveName);
//                    TrainPOJO tpj = new TrainPOJO(trainRunCode, trainRunType, allN, trainRunDepartName, trainRunArriveName, trainRunDepartTime, trainRunArriveTime);
//                    trainDict.put(trainRunCode, tpj);
//                }
//                try {
//                    ObjectOutputStream trainDictStream = new ObjectOutputStream(new FileOutputStream(dictFile));
//                    trainDictStream.writeObject(trainDict);
//                    trainDictStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        JsonObject res = new JsonObject();
        JsonObject result = new JsonObject();
        JsonArray data = new JsonArray();

        /* 标志量 */
        boolean train_no_flag = (train_no != null && !train_no.equals(""));
        boolean train_type_flag = (train_type != null && !train_type.equals(""));
        boolean depart_station_flag = (depart_station != null && !depart_station.equals(""));
        boolean arrive_station_flag = (arrive_station != null && !arrive_station.equals(""));
        boolean depart_time_flag = (depart_time != null && !depart_time.equals(""));
        boolean arrive_time_flag = (arrive_time != null && !arrive_time.equals(""));

        List<TrainRun> all_train = trainRunService.getAllTrainRun();
        int n = all_train.size();     /* 总数量 */
        int total_page = 0;   /* 页数上取整 */
        int st = (page_no - 1) * page_size + 1;     /* 开始条目 */
        int ed = page_no * page_size;               /* 终止条目 */
        int t = 0;                                  /* 满足搜索条件的个数 */

//        String st_station_name = depart_station_flag ? depart_station : null;
//        String ed_station_name = arrive_station_flag ? arrive_station : null;
//        String st_station_code = null;
//        String ed_station_code = null;

//        Map<String, TrainPOJO> trainDict = new TrainDictionary().getTrainDictionary();

        for (int i = 0; i < n; i++) {

            TrainRun trainRun = all_train.get(i);
            String trainRunCode = trainRun.getRun_code();
            Character trainRunType = trainRun.getType();
            int allN = trainRun.getStation_num();
            Station trainRunDepartStation = null;
            Station trainRunArriveStation = null;
            String trainRunDepartName = null;
            String trainRunArriveName = null;
            if (arrive_station_flag || depart_station_flag) {
                trainRunDepartStation = stationService.getStationByCode(stationOnLineService.getOneStation(trainRunCode, 1));
                trainRunArriveStation = stationService.getStationByCode(stationOnLineService.getOneStation(trainRunCode, allN));
                trainRunDepartName = trainRunDepartStation.getName();
                trainRunArriveName = trainRunArriveStation.getName();
            }

            if (train_no_flag && !trainRunCode.equals(train_no))
                continue;
            if (train_type_flag && trainRunType != train_type)
                continue;
            if (depart_station_flag) {
                boolean flag = false;
                if (trainRunDepartName.equals(depart_station)) {
                    flag = true;
                }
                if (!flag) continue;
            }
            if (arrive_station_flag) {
                boolean flag = false;
                if (trainRunArriveName.equals(arrive_station)) {
                    flag = true;
                }
                if (!flag) continue;
            }
            t++;
            if (t >= st && t <= ed) {
                if (trainRunDepartStation == null) {
                    trainRunDepartStation = stationService.getStationByCode(stationOnLineService.getOneStation(trainRunCode, 1));
                    trainRunDepartName = trainRunDepartStation.getName();
                }
                if (trainRunArriveStation == null) {
                    trainRunArriveStation = stationService.getStationByCode(stationOnLineService.getOneStation(trainRunCode, allN));
                    trainRunArriveName = trainRunArriveStation.getName();
                }
                JsonObject jot = new JsonObject();
                jot.addProperty("id", t);
                jot.addProperty("key", t);
                Date st_date = timeTableService.getDepartTime(trainRunCode, trainRunDepartStation.getCode());
                Date ed_date = timeTableService.getArrivalTime(trainRunCode, trainRunArriveStation.getCode());
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

                jot.addProperty("arriveTime", sdf.format(st_date));
                jot.addProperty("departTime", sdf.format(ed_date));
                jot.addProperty("editable", false);
                jot.addProperty("stationInfo", trainRunDepartName + " - " + trainRunArriveName);
                jot.addProperty("stationNum", allN);
                jot.addProperty("status", 0);
                jot.addProperty("trainNo", trainRunCode);
                jot.addProperty("trainType", trainRunType);
                data.add(jot);
            }
        }

        total_page = (t + page_size - 1) / page_size;
        result.addProperty("pageSize", page_size);
        result.addProperty("pageNo", page_no);
        result.addProperty("totalCount", t);
        result.addProperty("totalPage", total_page);
//        for (int i = st; i <= ed; i++) {
//            TrainRun temp = all_train.get(i - 1);
//            String run_code_res = temp.getRun_code();
//            Integer station_num_res = temp.getStation_num();
//            Character train_type_res = temp.getType();
//            List<Station> all_station = stationOnLineService.getAllStation(run_code_res);
//            st_station_name = all_station.get(0).getName();
//            ed_station_name = all_station.get(station_num_res - 1).getName();
//            st_station_code = all_station.get(0).getCode();
//            ed_station_code = all_station.get(station_num_res - 1).getCode();
//            JsonObject jot = new JsonObject();
//            jot.addProperty("id", i);
//            jot.addProperty("key", i);
//            Date st_date = timeTableService.getDepartTime(run_code_res, st_station_code);
//            Date ed_date = timeTableService.getArrivalTime(run_code_res, ed_station_code);
//            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//
//            jot.addProperty("arriveTime", sdf.format(st_date));
//            jot.addProperty("departTime", sdf.format(ed_date));
//            jot.addProperty("editable", false);
//            jot.addProperty("stationInfo", st_station_name + " - " + ed_station_name);
//            jot.addProperty("stationNum", station_num_res);
//            jot.addProperty("status", 0);
//            jot.addProperty("trainNo", run_code_res);
//            jot.addProperty("trainType", train_type_res);
//            data.add(jot);
//        }
        result.add("data", data);
        res.add("result", result);
        return res;
    }

}
