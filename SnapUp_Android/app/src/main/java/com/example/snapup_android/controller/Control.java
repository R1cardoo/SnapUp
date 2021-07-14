package com.example.snapup_android.controller;

import com.google.gson.JsonObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/usr")
public class Control {
    //JsonObject object = new JsonObject();
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public JsonObject add_restricted_usr(@RequestBody JsonObject object) {

        JsonObject res = new JsonObject();
        JsonObject result = new JsonObject();
        result.addProperty("error", true);
        result.addProperty("reason", "当前号码已存在");
        res.addProperty("message", "");
        res.addProperty("timestamp", "1");
        res.add("result", result);
        res.addProperty("code", String.valueOf(object.get("id")));
        return res;
    }
}
