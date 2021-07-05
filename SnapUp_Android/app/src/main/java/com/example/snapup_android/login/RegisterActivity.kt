package com.example.snapup_android.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.snapup_android.Homepage.HomepageActivity
import com.example.snapup_android.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerButton = findViewById<Button>(R.id.register_button)
        registerButton.setOnClickListener {
            //网络请求，是否成功？？？ 更改isSuccess
            val isSuccess = true
            if (isSuccess){

                val bundle = Bundle()
                //bundle.putString(nickname, )
                val intent = Intent(this, HomepageActivity::class.java)
                intent . putExtras(bundle)
                startActivity(intent)
                Toast.makeText(this,"you have registered successfully",Toast.LENGTH_SHORT).show()
            }


        }

    }
}