package com.example.snapup_android.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
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
import com.example.snapup_android.Homepage.HomepageActivity
import com.example.snapup_android.Http
import com.example.snapup_android.R.id
import com.example.snapup_android.R.layout
import com.example.snapup_android.User
import com.example.snapup_android.data.gsonClass.RegisterGson
import com.example.snapup_android.data.gsonClass.SettingsGson
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.user
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

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_settings)

        val usernameView = findViewById<EditText>(id.editText01)
        val passwordView = findViewById<EditText>(id.editText02)
        val nicknameView = findViewById<EditText>(id.editText03)
        val identityView = findViewById<EditText>(id.editText04)
        val nameView = findViewById<EditText>(id.editText05)
        val numberView = findViewById<EditText>(id.editText06)
        val mailView = findViewById<EditText>(id.editText07)
        val genderView = findViewById<NiceSpinner>(id.editText08)
        val edit = findViewById<Button>(id.Edit_Settings)
        val uploadButton = findViewById<Button>(id.Upload_Settings)



        usernameView.setText(User.username)       //从bundle中取出来
        passwordView.setText(User.password)
        nicknameView.setText(User.nickname)
        identityView.setText(User.identity)
        nameView.setText(User.name)       //从bundle中取出来
        numberView.setText(User.number)
        mailView.setText(User.mail)
        genderView.text = User.gender

        edit.setOnClickListener {
            passwordView.isEnabled= true
            nicknameView.isEnabled= true
            identityView.isEnabled= true
            nameView.isEnabled= true
            numberView.isEnabled= true
            mailView.isEnabled= true
            genderView.isEnabled= true

        }
        val genderList: Array<String> = arrayOf("M", "F")
        val genderAdapter = ArrayAdapter<String>(this, layout.activity_settings , genderList)
        findViewById<NiceSpinner>(id.editText08).setAdapter(genderAdapter)
        findViewById<NiceSpinner>(id.editText08).onSpinnerItemSelectedListener = OnSpinnerItemSelectedListener{ _: NiceSpinner, view: View, _: Int, _: Long ->
            fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem: String = parent.getItemAtPosition(position).toString()
            }
        }


        passwordView.afterTextChanged {
            if(usernameView.length()>3 && passwordView.length()>5) uploadButton.isEnabled = true
        }
        nicknameView.afterTextChanged {
            if(usernameView.length()>3 && passwordView.length()>5) uploadButton.isEnabled = true
        }
        identityView.afterTextChanged {
            if(usernameView.length()>3 && passwordView.length()>5) uploadButton.isEnabled = true
        }
        nameView.afterTextChanged {
            if(usernameView.length()>3 && passwordView.length()>5) uploadButton.isEnabled = true
        }
        numberView.afterTextChanged {
            if(usernameView.length()>3 && passwordView.length()>5) uploadButton.isEnabled = true
        }
        mailView.afterTextChanged {
            if(usernameView.length()>3 && passwordView.length()>5) uploadButton.isEnabled = true
        }


        uploadButton.setOnClickListener {
            //提交修改！！如果合法跳转，不合法toast
            val mime = "application/json;charset=utf-8".toMediaTypeOrNull()
            val json = JSONObject()
                .put("usrname", usernameView.text.toString())
                .put("identity",identityView.text.toString())
                .put("name",nameView.text.toString())
                .put("pwd",passwordView.text.toString())
                .put("nickname",nicknameView.text.toString())
                .put("mail",mailView.text.toString())
                .put("tele",numberView.text.toString())

            val request = Request.Builder()//创建Request 对象。
                .url("${Http.prefix}/api/train/person-info-change/")
                .post(json.toString().toRequestBody(mime)).build()//传递请求体

            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("UPDATE", "onFailure: $e")
                    Looper.prepare()
                    Toast.makeText(this@SettingsActivity, "Http Request Failed", Toast.LENGTH_SHORT).show()
                    Looper.loop()
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val settingsGson = Gson().fromJson(response.body?.string(), SettingsGson::class.java)
                    Log.d("UPDATE", "OnResponse: $settingsGson")
                    //do something in mine thread
                    try {
                        if(settingsGson.result == true) {
                            passwordView.clearFocus()
                            passwordView.isEnabled = false
                            nicknameView.clearFocus()
                            nicknameView.isEnabled = false
                            identityView.clearFocus()
                            identityView.isEnabled = false
                            nameView.clearFocus()
                            nameView.isEnabled = false
                            numberView.clearFocus()
                            numberView.isEnabled = false
                            mailView.clearFocus()
                            mailView.isEnabled = false
                            genderView.clearFocus()
                            genderView.isEnabled = false
                            uploadButton.clearFocus()
                            uploadButton.isEnabled =false
                        }
                        Looper.prepare()

                        if(settingsGson.result==true){
                            Toast.makeText(this@SettingsActivity, "you updated your information successfully", Toast.LENGTH_SHORT).show()
                        }else Toast.makeText(this@SettingsActivity , "Change User Information Failed", Toast.LENGTH_SHORT).show()

                        Looper.loop()


                    }catch (e:Exception){
                        e.printStackTrace()
                    }
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
