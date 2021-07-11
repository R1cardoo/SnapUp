package com.example.snapup_android.settings

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType.TYPE_CLASS_NUMBER
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.snapup_android.R.id
import com.example.snapup_android.R.layout
import kotlinx.android.synthetic.main.activity_login.username

class SettingsActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_settings)

        val bundle = intent.extras

        val edit = findViewById<Button>(id.Edit_Settings)
        val uploadButton = findViewById<Button>(id.Upload_Settings)

        val username = findViewById<EditText>(id.editText01)
        val password = findViewById<EditText>(id.editText02)
        val nickname = findViewById<EditText>(id.editText03)
        val identity = findViewById<EditText>(id.editText04)
        val name = findViewById<EditText>(id.editText05)
        val number = findViewById<EditText>(id.editText06)
        val mail = findViewById<EditText>(id.editText07)
        val gender = findViewById<EditText>(id.editText08)

        username.setText(intent.getStringExtra("username"))       //从bundle中取出来
        password.setText(intent.getStringExtra("password"))
        nickname.setText(intent.getStringExtra("nickname"))
        identity.setText(intent.getStringExtra("identity"))
        name.setText(intent.getStringExtra("name"))       //从bundle中取出来
        number.setText(intent.getStringExtra("number"))
        mail.setText(intent.getStringExtra("mail"))
        gender.setText(intent.getStringExtra("gender"))

        edit.setOnClickListener {
            username.isEnabled= true
            password.isEnabled= true
            nickname.isEnabled= true
            identity.isEnabled= true
            name.isEnabled= true
            number.isEnabled= true
            mail.isEnabled= true
            gender.isEnabled= true

        }
        username.afterTextChanged {
            if(username.length()>5 && password.length()>5) uploadButton.isEnabled = true
        }
        password.afterTextChanged {
            if(username.length()>5 && password.length()>5) uploadButton.isEnabled = true
        }
        nickname.afterTextChanged {
            if(username.length()>5 && password.length()>5) uploadButton.isEnabled = true
        }
        identity.afterTextChanged {
            if(username.length()>5 && password.length()>5) uploadButton.isEnabled = true
        }
        name.afterTextChanged {
            if(username.length()>5 && password.length()>5) uploadButton.isEnabled = true
        }
        number.afterTextChanged {
            if(username.length()>5 && password.length()>5) uploadButton.isEnabled = true
        }
        mail.afterTextChanged {
            if(username.length()>5 && password.length()>5) uploadButton.isEnabled = true
        }
        gender.afterTextChanged {
            if(username.length()>5 && password.length()>5) uploadButton.isEnabled = true
        }

        uploadButton.setOnClickListener {
            //提交修改！！如果合法跳转，不合法toast
            val isSuccess = true
            if(isSuccess) {
                //修改成功，刷新信息
                Toast.makeText(this, "you updated your information successfully", Toast.LENGTH_SHORT).show()
            }else {
                username.isEnabled= false
                password.isEnabled= false
                nickname.isEnabled= false
                identity.isEnabled= false
                name.isEnabled= false
                number.isEnabled= false
                mail.isEnabled= false
                gender.isEnabled= false
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
