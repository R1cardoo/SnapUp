package com.example.snapup_android.login

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.snapup_android.Homepage.HomepageActivity
import com.example.snapup_android.Http
import com.example.snapup_android.R
import com.example.snapup_android.R.id
import com.example.snapup_android.R.layout
import com.example.snapup_android.User
import com.example.snapup_android.data.gsonClass.RegisterGson
import com.example.snapup_android.data.gsonClass.UserGson
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.angmarch.views.NiceSpinner
import org.angmarch.views.OnSpinnerItemSelectedListener
import org.json.JSONObject
import java.io.IOException

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val context = this
        val registerButton = findViewById<Button>(R.id.register_button)
        val numberOrMail = findViewById<EditText>(R.id.editText1)
        val password = findViewById<EditText>(R.id.editText2)
        val nickname = findViewById<EditText>(R.id.editText3)
        val identity = findViewById<EditText>(R.id.editText4)
        val name = findViewById<EditText>(R.id.editText5)
        val gender = findViewById<NiceSpinner>(R.id.gender)

        val genderList: Array<String> = arrayOf("M", "F")
        val genderAdapter = ArrayAdapter<String>(this,R.layout.activity_register , genderList)
        findViewById<NiceSpinner>(id.gender).setAdapter(genderAdapter)
        findViewById<NiceSpinner>(id.gender).onSpinnerItemSelectedListener = OnSpinnerItemSelectedListener{ _: NiceSpinner, view: View, _: Int, _: Long ->
            fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem: String = parent.getItemAtPosition(position).toString()
            }
        }
        numberOrMail.afterTextChanged {
            if(numberOrMail.length()>5 && password.length()>5) registerButton.isEnabled = true
        }
        password.afterTextChanged {
            if(numberOrMail.length()>5 && password.length()>5) registerButton.isEnabled = true
        }
        registerButton.setOnClickListener {
            //网络请求，是否成功？？？ 返回完整信息
            val mime = "application/json;charset=utf-8".toMediaTypeOrNull()
            val json = JSONObject()
                .put("usrname",numberOrMail.text.toString())
                .put("identity",identity.text.toString())
                .put("gender",gender.text.toString())
                .put("name",name.text.toString())
                .put("pwd",password.text.toString())
                .put("nickname",nickname.text.toString())
            if(numberOrMail.text.toString().contains('@')){
                json.put("mail", numberOrMail.text.toString())
                    .put("tele", "")
            }else{
                json.put("tele", numberOrMail.text.toString())
                    .put("mail", "")
            }

            val request = Request.Builder()//创建Request 对象。
                .url("${Http.prefix}/api/train/registerUsr/")
                .post(json.toString().toRequestBody(mime)).build()//传递请求体

            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("UPDATE", "onFailure: $e")
                    Looper.prepare()
                    Toast.makeText(this@RegisterActivity, "Http Request Failed", Toast.LENGTH_SHORT).show()
                    Looper.loop()
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val registerGson = Gson().fromJson(response.body?.string(), RegisterGson::class.java)
                    Log.d("UPDATE", "OnResponse: $registerGson")
                    Looper.prepare()
                    //do something in mine thread
                    if(registerGson.result==true){
                        User.username = numberOrMail.text.toString()
                        User.password =  password.text.toString()
                        User.identity = identity.text.toString()
                        User.gender =  gender.text.toString()
                        User.name = name.text.toString()
                        User.nickname =  nickname.toString()

                        if(numberOrMail.text.toString().contains('@')){
                            User.mail = numberOrMail.text.toString()
                            User.number =  ""
                        }else{
                            User.number = numberOrMail.text.toString()
                            User.mail =  ""
                        }

                        val intent = Intent(context, HomepageActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(context,"You Have Registered Successfully",Toast.LENGTH_SHORT).show()
                    }else Toast.makeText(this@RegisterActivity , "Register Failed", Toast.LENGTH_SHORT).show()
                    Looper.loop()
                }
            })

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