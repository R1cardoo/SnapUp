package com.example.snapup_android.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.snapup_android.MyService
import com.example.snapup_android.R.id
import com.example.snapup_android.R.layout

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_settings)


        val edit = findViewById<Button>(id.Edit_Settings)
        val uploadButton = findViewById<Button>(id.Upload_Settings)

        val usernameView = findViewById<EditText>(id.editText01)
        val passwordView = findViewById<EditText>(id.editText02)
        val nicknameView = findViewById<EditText>(id.editText03)
        val identityView = findViewById<EditText>(id.editText04)
        val nameView = findViewById<EditText>(id.editText05)
        val numberView = findViewById<EditText>(id.editText06)
        val mailView = findViewById<EditText>(id.editText07)
        val genderView = findViewById<EditText>(id.editText08)

        usernameView.setText(MyService.user.username)       //从bundle中取出来
        passwordView.setText(MyService.user.password)
        nicknameView.setText(MyService.user.nickname)
        identityView.setText(MyService.user.identity)
        nameView.setText(MyService.user.name)       //从bundle中取出来
        numberView.setText(MyService.user.number)
        mailView.setText(MyService.user.mail)
        if(MyService.user.gender == 'a') genderView.setText("male")
        else genderView.setText("female")


        edit.setOnClickListener {
            usernameView.isEnabled= true
            passwordView.isEnabled= true
            nicknameView.isEnabled= true
            identityView.isEnabled= true
            nameView.isEnabled= true
            numberView.isEnabled= true
            mailView.isEnabled= true
            genderView.isEnabled= true

        }
        usernameView.afterTextChanged {
            if(usernameView.length()>5 && passwordView.length()>5) uploadButton.isEnabled = true
        }
        passwordView.afterTextChanged {
            if(usernameView.length()>5 && passwordView.length()>5) uploadButton.isEnabled = true
        }
        nicknameView.afterTextChanged {
            if(usernameView.length()>5 && passwordView.length()>5) uploadButton.isEnabled = true
        }
        identityView.afterTextChanged {
            if(usernameView.length()>5 && passwordView.length()>5) uploadButton.isEnabled = true
        }
        nameView.afterTextChanged {
            if(usernameView.length()>5 && passwordView.length()>5) uploadButton.isEnabled = true
        }
        numberView.afterTextChanged {
            if(usernameView.length()>5 && passwordView.length()>5) uploadButton.isEnabled = true
        }
        mailView.afterTextChanged {
            if(usernameView.length()>5 && passwordView.length()>5) uploadButton.isEnabled = true
        }
        genderView.afterTextChanged {
            if(usernameView.length()>5 && passwordView.length()>5) uploadButton.isEnabled = true
        }

        uploadButton.setOnClickListener {
            //提交修改！！如果合法跳转，不合法toast
            MyService.user.username =usernameView.text.toString()
            MyService.user.password =passwordView.text.toString()
            MyService.user.identity =identityView.text.toString()
            MyService.user.nickname =nicknameView.text.toString()
            if(genderView.text.toString() == "male")  MyService.user.gender = 'a'
            else MyService.user.gender = 'b'
            MyService.user.name =nameView.text.toString()
            MyService.user.mail =mailView.text.toString()
            MyService.user.number =numberView.text.toString()
            val isSuccess = MyService.getUserService().updateUserInfo(MyService.user)
            if(isSuccess) {
                //修改成功，刷新信息
                Toast.makeText(this, "you updated your information successfully", Toast.LENGTH_SHORT).show()
                usernameView.isEnabled= false
                passwordView.isEnabled= false
                nicknameView.isEnabled= false
                identityView.isEnabled= false
                nameView.isEnabled= false
                numberView.isEnabled= false
                mailView.isEnabled= false
                genderView.isEnabled= false
            }else {
                Toast.makeText(this, "your information is illegal", Toast.LENGTH_SHORT).show()
            }
        }

    }
    override fun onNewIntent(intent: Intent)
    {
        super.onNewIntent(intent);
        setIntent(intent);//设置新的intent
    }
    /**
     * Extension function to simplify setting an afterTextChanged action to EditText components.
     */
    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }
}
