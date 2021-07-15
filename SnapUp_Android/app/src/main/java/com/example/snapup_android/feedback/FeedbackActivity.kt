package com.example.snapup_android.feedback

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.snapup_android.MyService
import com.example.snapup_android.R
import com.example.snapup_android.pojo.FeedBack
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class FeedbackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
        setSupportActionBar(findViewById(R.id.toolbar))

        val comment = findViewById<EditText>(R.id.textView2)
        findViewById<Button>(R.id.Publish).setOnClickListener {  view ->
            //添加网络请求。
            val feedback = FeedBack()
            feedback.username = MyService.user.username
            feedback.comment = comment.text.toString()
            feedback.tele = MyService.user.number

            val isSuccess = MyService.getFeedbackService().createFeedBack(feedback)
            if(isSuccess) Toast.makeText(this,"your feedback delivered successfully!",Toast.LENGTH_SHORT).show()

        }
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}