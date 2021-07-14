package com.snapup.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.snapup.pojo.RestrictedUsr;
import com.snapup.service.RestrictedUsrService;
import com.snapup.service.RestrictedUsrServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class Control {
    @Autowired
    @Qualifier("restrictedUsrService")
    private RestrictedUsrService restrictedUsrService;


    //private RestrictedUsrService restrictedUsrService;


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
        boolean train_type_flag = (train_type != null && !train_type.equals(""));
        boolean depart_station_flag = (depart_station != null && !depart_station.equals(""));
        boolean arrive_station_flag = (arrive_station != null && !arrive_station.equals(""));
        boolean depart_time_flag = (depart_time != null && !depart_time.equals(""));
        boolean arrive_time_flag = (arrive_time != null && !arrive_time.equals(""));

        List<RestrictedUsr> all_usr = restrictedUsrService.findAllRestrictedUsr();
        int n = all_usr.size();     /* 总数量 */
        int total_page = (n + page_size - 1) / page_size;   /* 页数上取整 */
        int st = (page_no - 1) * page_size + 1;     /* 开始条目 */
        int ed = page_no * page_size;               /* 终止条目 */
        int t = 0;                                  /* 满足搜索条件的个数 */
        for (int i = 0; i < n; i++) {

        }

        return res;
    }

}
