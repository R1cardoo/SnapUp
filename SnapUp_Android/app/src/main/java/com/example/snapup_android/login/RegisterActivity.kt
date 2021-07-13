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
        val numberOrMail = findViewById<EditText>(R.id.editText1)
        val password = findViewById<EditText>(R.id.editText2)
        val nickname = findViewById<EditText>(R.id.editText3)
        val identity = findViewById<EditText>(R.id.editText4)
        val name = findViewById<EditText>(R.id.editText5)
        val gender = findViewById<EditText>(R.id.editText6)

        numberOrMail.afterTextChanged {
            if(numberOrMail.length()>10 && password.length()>5) registerButton.isEnabled = true
        }
        password.afterTextChanged {
            if(numberOrMail.length()>10 && password.length()>5) registerButton.isEnabled = true
        }
        registerButton.setOnClickListener {
            //网络请求，是否成功？？？ 返回完整信息
            val isSuccess = true
            if (isSuccess){

                val bundle = Bundle()
                //输入真实数据网球请求返回的数据。
                bundle.putString("username", numberOrMail.text.toString())
                bundle.putString("password", password.text.toString())
                bundle.putString("identity", identity.text.toString())
                bundle.putString("gender", gender.text.toString())
                bundle.putString("name", name.text.toString())
                if(numberOrMail.text.toString().contains('@')){
                    bundle.putString("mail", numberOrMail.text.toString())
                    bundle.putString("number", "")
                }else{
                    bundle.putString("number", numberOrMail.text.toString())
                    bundle.putString("mail", "")
                }
                bundle.putString("nickname", nickname.text.toString() )

                val intent = Intent(this, HomepageActivity::class.java)
                intent.putExtras(bundle)
                this.startActivity(intent)
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