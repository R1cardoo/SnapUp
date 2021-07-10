package com.example.snapup_android.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType.TYPE_CLASS_NUMBER
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.snapup_android.R
import kotlinx.android.synthetic.main.activity_register.textView1

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val edit = findViewById<Button>(R.id.Edit_Settings)
        val uploadButton = findViewById<Button>(R.id.Upload_Settings)
        val id = findViewById<EditText>(R.id.editText1)
        val password = findViewById<EditText>(R.id.editText2)
        val nickname = findViewById<EditText>(R.id.editText3)
        val identity = findViewById<EditText>(R.id.editText4)

        id.setText("4008 000 000")       //从bundle中取出来
        password.setText("123456bjut")
        nickname.setText("froyo")
        identity.setText("18071101")

        edit.setOnClickListener {
            id.isEnabled= true
            password.isEnabled= true
            nickname.isEnabled= true
            identity.isEnabled= true

            id.inputType = TYPE_CLASS_TEXT
            password.inputType = TYPE_CLASS_TEXT
            nickname.inputType = TYPE_CLASS_TEXT
            identity.inputType = TYPE_CLASS_NUMBER
        }
        id.afterTextChanged {
            if(id.length()>5 && password.length()>5) uploadButton.isEnabled = true
        }
        password.afterTextChanged {
            if(id.length()>5 && password.length()>5) uploadButton.isEnabled = true
        }
        id.afterTextChanged {
            if(id.length()>5 && password.length()>5) uploadButton.isEnabled = true
        }
        password.afterTextChanged {
            if(id.length()>5 && password.length()>5) uploadButton.isEnabled = true
        }


        uploadButton.setOnClickListener {
            //提交修改！！如果合法跳转，不合法toast
            val isSuccess = true
            if(isSuccess) {
                //修改成功，刷新信息
                Toast.makeText(this, "you updated your information successfully", Toast.LENGTH_SHORT).show()
            }else Toast.makeText(this, "your information is illegal", Toast.LENGTH_SHORT).show()
        }

    }

    /**
     * Extension function to simplify setting an afterTextChanged action to EditText components.
     */
    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }
}
