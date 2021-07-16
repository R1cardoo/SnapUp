package com.example.snapup_android.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.snapup_android.Homepage.HomepageActivity
import com.example.snapup_android.Http
import com.example.snapup_android.R
import com.example.snapup_android.User
import com.example.snapup_android.data.gsonClass.UserGson
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val register = findViewById<Button>(R.id.register)

        register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java )
            startActivity(intent)
        }
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }
        }
        login.setOnClickListener {
            val mime = "application/json;charset=utf-8".toMediaTypeOrNull()

            val json = JSONObject()
                .put("username",username.text.toString())
                .put("pwd",password.text.toString()).toString()

            val request = Request.Builder()//创建Request 对象。
                .url("${Http.prefix}/api/train/getUsr/")
                .post(json.toRequestBody(mime)).build()//传递请求体
            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("UPDATE", "onFailure: $e")
                    Looper.prepare()
                    Toast.makeText(this@LoginActivity , "Http Request Failed", Toast.LENGTH_SHORT).show()
                    Looper.loop()
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {

                    Looper.prepare()
                    val userGson = Gson().fromJson(response.body?.string(), UserGson::class.java)
                    Log.d("UPDATE", "OnResponse: $userGson")
                    if(userGson.usrname != "-1") updateUiWithUser(userGson)
                    else Toast.makeText(this@LoginActivity , "Login Failed", Toast.LENGTH_SHORT).show()
                    //do something in mine thread
                    Looper.loop()
                }
            })
        }


    }
    private fun updateUiWithUser(model:LoggedInUserView?) {
        val welcome = getString(R.string.welcome)
        // TODO : initiate successful logged in experience
        //点击事件里 调用后端方法
        User.nickname = "handsomeBoy"
        User.mail = "1127676571@qq.com"
        User.number = "13552643675"
        User.name = "ricardo"
        User.gender = "male"
        User.identity = "18071102"
        User.password = "123456"
        User.username = "makabaka"
        val intent = Intent(this, HomepageActivity::class.java)
        startActivity(intent)
        val displayName = User.nickname
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }
    private fun updateUiWithUser(userGson: UserGson) {
        val welcome = getString(R.string.welcome)
        // TODO : initiate successful logged in experience
        //点击事件里 调用后端方法
        User.nickname = userGson.nickname
        User.mail = userGson.mail
        User.number = userGson.tele
        User.name = userGson.name
        User.gender = userGson.gender
        User.identity = userGson.identity
        User.password = userGson.pwd
        User.username = userGson.usrname
        val intent = Intent(this, HomepageActivity::class.java)
        startActivity(intent)
        val displayName = User.nickname
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
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