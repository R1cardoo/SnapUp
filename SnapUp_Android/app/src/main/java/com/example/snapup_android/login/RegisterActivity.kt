package com.example.snapup_android.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.snapup_android.Homepage.HomepageActivity
import com.example.snapup_android.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerButton = findViewById<Button>(R.id.register_button)
        val id = findViewById<EditText>(R.id.editText1)
        val password = findViewById<EditText>(R.id.editText2)
        val nickname = findViewById<EditText>(R.id.editText3)
        val identity = findViewById<EditText>(R.id.editText4)

        id.afterTextChanged {
            if(id.length()>5 && password.length()>5) registerButton.isEnabled = true
        }
        password.afterTextChanged {
            if(id.length()>5 && password.length()>5) registerButton.isEnabled = true
        }
        registerButton.setOnClickListener {
            //网络请求，是否成功？？？ 返回完整信息
            val isSuccess = true
            if (isSuccess){

                val bundle = Bundle()
                //输入真实数据
                bundle.putString("username", "18071102")
                bundle.putString("password", "123456")
                bundle.putString("identity", "18071102")
                bundle.putString("gender","male" )
                bundle.putString("name", "ricardo")
                bundle.putString("number", "13552643675")
                bundle.putString("mail", "1127676571@qq.com")
                bundle.putString("nickname","handsomeBoy" )

                //val intent = Intent(this, HomepageActivity::class.java)
                //intent.putExtras(bundle)
                //this.startActivity(intent)
                HomepageActivity().startActivity(this, bundle)
                Toast.makeText(this,"you have registered successfully",Toast.LENGTH_SHORT).show()
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