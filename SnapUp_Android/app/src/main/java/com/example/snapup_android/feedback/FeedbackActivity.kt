package com.example.snapup_android.feedback

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.snapup_android.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class FeedbackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        setSupportActionBar(findViewById(R.id.toolbar))

        val bundle = intent.extras
        //数据
        val username = bundle?.getString("username")
        val password = bundle?.getString("password")
        val identity = bundle?.getString("identity")
        val gender = bundle?.getString("gender")
        val name = bundle?.getString("name")
        val number = bundle?.getString("number")
        val mail = bundle?.getString("mail")
        val nickname = bundle?.getString("nickname")


        findViewById<Button>(R.id.Publish).setOnClickListener {  view ->
            //添加网络请求。

            val isSuccess = true
            if(isSuccess) Toast.makeText(this,"your feedback delivered successfully!",Toast.LENGTH_SHORT).show()

        }
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}