package com.example.snapup_android.feedback

import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.snapup_android.Http
import com.example.snapup_android.R
import com.example.snapup_android.User
import com.example.snapup_android.data.gsonClass.FeedbackGson
import com.example.snapup_android.data.gsonClass.RegisterGson
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class FeedbackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        setSupportActionBar(findViewById(R.id.toolbar))
        
        findViewById<Button>(R.id.Publish).setOnClickListener {  view ->
            //添加网络请求。
            requestFeedback()
        }
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    fun requestFeedback(){
        val mime = "application/json;charset=utf-8".toMediaTypeOrNull()

        val json = JSONObject()
            .put("username",User.username)
            .put("tele",User.number)
            .put("comment",findViewById<EditText>(R.id.textView2).text.toString()).toString()

        val request = Request.Builder()//创建Request 对象。
            .url("${Http.prefix}/api/train/submit-feedback/")
            .post(json.toRequestBody(mime)).build()//传递请求体

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("UPDATE", "onFailure: $e")
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val feedbackGson = Gson().fromJson(response.body?.string(), FeedbackGson::class.java)
                Log.d("UPDATE", "OnResponse: $feedbackGson" )
                Looper.prepare()
                Toast.makeText(this@FeedbackActivity , "Publish Feedback Success!", Toast.LENGTH_SHORT).show()
                //do something in mine thread
                Looper.loop()
            }
        })
    }
}