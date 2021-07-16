package com.example.snapup_android.Homepage

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.snapup_android.Http
import com.example.snapup_android.R
import com.example.snapup_android.data.gsonClass.RegisterGson
import com.example.snapup_android.data.gsonClass.ScheduleGson
import com.example.snapup_android.data.gsonClass.UserGson
import com.example.snapup_android.feedback.FeedbackActivity
import com.example.snapup_android.login.LoginActivity
import com.example.snapup_android.settings.SettingsActivity
import com.example.snapup_android.viewSchedule.ViewScheduleActivity
import com.example.snapup_android.viewOrder.ViewOrderActivity
import com.example.snapup_android.viewSchedule.content.ScheduleContentList
import com.example.snapup_android.viewSchedule.content.ScheduleContentList.TrainInfo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import okhttp3.Call
import okhttp3.Callback
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception


class HomepageActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_info, R.id.nav_feedback, R.id.nav_order, R.id.nav_quit
            ),drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener(OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    drawerLayout.closeDrawers()
                }
                R.id.nav_info -> {
                    requestSchedule()
                    val intent1 = Intent(this, ViewScheduleActivity::class.java)
                    startActivity(intent1)
                }
                R.id.nav_order -> {

                    val intent2 = Intent(this, ViewOrderActivity::class.java)
                    startActivity(intent2)
                }
                R.id.nav_feedback -> {
                    val intent3 = Intent(this, FeedbackActivity::class.java)
                    startActivity(intent3)
                }
                R.id.nav_settings -> {
                    val intent4 = Intent(this , SettingsActivity::class.java)
                    startActivity(intent4)
                }
                R.id.nav_quit -> {
                    val intent5 = Intent(this,LoginActivity::class.java)
                    startActivity(intent5)
                }

            }
            true
        })
    }

    fun requestSchedule(){
        val thisUrl = Http.prefix.toHttpUrlOrNull()!!.newBuilder()
            .addPathSegment("api")
            .addPathSegment("train")
            .addPathSegment("train-run-info")
            .build()
        val request = Request.Builder()//创建Request 对象。
            .url(thisUrl)
            .build()//传递请求体
        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("UPDATE", "onFailure: $e")
                Looper.prepare()
                Toast.makeText(this@HomepageActivity, "Http Request Failed", Toast.LENGTH_SHORT).show()
                Looper.loop()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                //Log.d("UPDATE", "OnResponse: " + response.body?.string())
                try {
                    val jsonArray = JsonParser().parse(response.body?.string()).getAsJsonArray()
                    for(i in 0 until jsonArray.size()){
                        val trainInfo = Gson().fromJson(jsonArray.get(i).toString(), TrainInfo::class.java)
                        ScheduleContentList.addScheduleItem(trainInfo)
                    }
                }catch (e : Exception){
                    e.printStackTrace()
                }

            }
        })
    }
    override fun onNewIntent(intent: Intent)
    {
        super.onNewIntent(intent);
        setIntent(intent);//设置新的intent
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.homepage, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}