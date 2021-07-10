package com.example.snapup_android.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType.TYPE_CLASS_TEXT
import android.widget.Button
import android.widget.EditText
import com.example.snapup_android.R
import kotlinx.android.synthetic.main.activity_register.textView1

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val edit = findViewById<Button>(R.id.Edit_Settings)
        val upload = findViewById<Button>(R.id.Upload_Settings)
        val id = findViewById<EditText>(R.id.editText1)
        val password = findViewById<EditText>(R.id.editText2)
        val nickname = findViewById<EditText>(R.id.editText3)
        val identity = findViewById<EditText>(R.id.editText4)

        edit.setOnClickListener {
            id.inputType = TYPE_CLASS_TEXT
            password.inputType = TYPE_CLASS_TEXT
            nickname.inputType = TYPE_CLASS_TEXT
            identity.inputType = TYPE_CLASS_TEXT
        }

        upload.setOnClickListener {
            //提交修改！！
        }

    }
}