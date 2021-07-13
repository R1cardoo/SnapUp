package com.snapup.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.snapup.pojo.RestrictedUsr;
import com.snapup.service.RestrictedUsrService;
import com.snapup.service.RestrictedUsrServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class Control {
    @Autowired
    @Qualifier("restrictedUsrService")
    private RestrictedUsrService restrictedUsrService;


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
    public JsonObject search_credit(@RequestBody JsonObject jo) {
        JsonObject res = new JsonObject();
        JsonObject result = new JsonObject();
        JsonObject data = new JsonObject();
        int page_no = jo.get("pageNo").getAsInt();
        int page_size = jo.get("pageSize").getAsInt();
        List<RestrictedUsr> all_usr = restrictedUsrService.findAllRestrictedUsr();
        int n = all_usr.size();     /* 总数量 */

        return res;
    }
}
