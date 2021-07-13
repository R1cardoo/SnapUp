package com.example.snapup_android.Homepage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.snapup_android.R
import com.example.snapup_android.feedback.FeedbackActivity
import com.example.snapup_android.login.LoginActivity
import com.example.snapup_android.settings.SettingsActivity
import com.example.snapup_android.viewSchedule.ViewScheduleActivity
import com.example.snapup_android.viewOrder.ViewOrderActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.google.android.material.snackbar.Snackbar


class HomepageActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        val bundle = intent.extras
        //数据
        val username = bundle?.getString("username")
        val password = bundle?.getString("password")
        val identity = bundle?.getString("identity")
        val gender = bundle?.getString("gender")
        val name = bundle?.getString("name")
        val number = bundle?.getString("number")
        val mail = bundle?.getString("mail")
        val nickname = bundle?.getString("nickname")

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
                    val bundle1 = Bundle()
                    //输入真实数据
                    bundle1.putString("username", username)
                    bundle1.putString("password", password)
                    bundle1.putString("identity", identity)
                    bundle1.putString("gender", gender   )
                    bundle1.putString("name", name)
                    bundle1.putString("number", number)
                    bundle1.putString("mail", mail)
                    bundle1.putString("nickname", nickname)
                    val intent1 = Intent(this, ViewScheduleActivity::class.java)
                    intent1.putExtras(bundle1)
                    startActivity(intent1)
                }
                R.id.nav_order -> {
                    val bundle2 = Bundle()
                    //输入真实数据
                    bundle2.putString("username", username)
                    bundle2.putString("password", password)
                    bundle2.putString("identity", identity)
                    bundle2.putString("gender", gender   )
                    bundle2.putString("name", name)
                    bundle2.putString("number", number)
                    bundle2.putString("mail", mail)
                    bundle2.putString("nickname", nickname)
                    val intent2 = Intent(this, ViewOrderActivity::class.java)
                    intent2.putExtras(bundle2)
                    startActivity(intent2)
                }
                R.id.nav_feedback -> {
                    val bundle3 = Bundle()
                    //输入真实数据
                    bundle3.putString("username", username)
                    bundle3.putString("password", password)
                    bundle3.putString("identity", identity)
                    bundle3.putString("gender", gender   )
                    bundle3.putString("name", name)
                    bundle3.putString("number", number)
                    bundle3.putString("mail", mail)
                    bundle3.putString("nickname", nickname)
                    val intent3 = Intent(this, FeedbackActivity::class.java)
                    intent3.putExtras(bundle3)
                    startActivity(intent3)
                }
                R.id.nav_settings -> {
                    val bundle4 = Bundle()
                    //输入真实数据
                    bundle4.putString("username", username)
                    bundle4.putString("password", password)
                    bundle4.putString("identity", identity)
                    bundle4.putString("gender", gender   )
                    bundle4.putString("name", name)
                    bundle4.putString("number", number)
                    bundle4.putString("mail", mail)
                    bundle4.putString("nickname", nickname)
                    val intent4 = Intent(this , SettingsActivity::class.java)
                    intent4.putExtras(bundle4)
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