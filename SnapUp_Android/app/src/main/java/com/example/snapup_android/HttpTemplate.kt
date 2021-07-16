package com.example.snapup_android

import android.os.Looper
import android.util.Log
import com.example.snapup_android.data.gsonClass.UserGson
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

object HttpTemplate {

    fun PostMethord(){
        val mime = "application/json;charset=utf-8".toMediaTypeOrNull()

        val json = JSONObject()
            .put("identity","18000")
            .put("name","zhangsan")
            .put("id","150").toString()

        val request = Request.Builder()//创建Request 对象。
            .url("${Http.prefix}/api/train/save-credit/")
            .post(json.toRequestBody(mime)).build()//传递请求体

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("UPDATE", "onFailure: $e")
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                Log.d("UPDATE", "OnResponse: " + response.body?.string())
                Looper.prepare()
                //do something in mine thread
                Looper.loop()
            }
        })
    }

    fun getMethod(){
        val thisUrl = Http.prefix.toHttpUrlOrNull()!!.newBuilder()
            .addPathSegment("api")
            .addPathSegment("train")
            .addPathSegment("credit")
            .addQueryParameter("pageNo","1")
            .addQueryParameter("pageSize","10")
            .build()
        val request = Request.Builder()//创建Request 对象。
            .url(thisUrl)
            .build()//传递请求体
        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("UPDATE", "onFailure: $e")
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                Log.d("UPDATE", "OnResponse: " + response.body?.string())
                Looper.prepare()
                val userGson = Gson().fromJson(response.body?.string(),UserGson::class.java)
                //do something in mine thread
                Looper.loop()
            }
        })
    }


}