package com.example.snapup_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.snapup_android.feedback.feedbackActivity
import com.example.snapup_android.ui.login.LoginActivity
import com.example.snapup_android.viewInfo.viewInfoActivity
import com.example.snapup_android.viewOrder.viewOrderActivity

//deprecated
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val order = findViewById<Button>(R.id.button)
        val info = findViewById<Button>(R.id.button2)
        val feedback = findViewById<Button>(R.id.button3)
        val quit = findViewById<Button>(R.id.button4)

        order.setOnClickListener {
            val intent = Intent(this,viewOrderActivity::class.java)
            startActivity(intent)
        }
        info.setOnClickListener {
            val intent = Intent(this, viewInfoActivity::class.java)
            startActivity(intent)
        }
        feedback.setOnClickListener {
            val intent = Intent(this, feedbackActivity::class.java)
            startActivity(intent)
        }
        quit.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

}