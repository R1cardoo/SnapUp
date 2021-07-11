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
    public val BUNDLE_KEY = "this is a bundle key"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        val i = intent
        val bundle = intent.extras
        //数据
        //val nickname = bundle?.getString("nickname")
        //val identity = bundle?.getString("identity")
        val username = intent.getStringExtra("username")

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
                    val intent = Intent(this, ViewScheduleActivity::class.java)
                    val bundle1 = Bundle()
                    //输入真实数据
                    bundle1.putString("username", "18071102")
                    bundle1.putString("password", "123456")
                    bundle1.putString("identity", "18071102")
                    bundle1.putString("gender","male" )
                    bundle1.putString("name", "ricardo")
                    bundle1.putString("number", "13552643675")
                    bundle1.putString("mail", "1127676571@qq.com")
                    bundle1.putString("nickname","handsomeBoy" )
                    intent.putExtras(bundle1)
                    startActivity(intent)
                }
                R.id.nav_order -> {
                    val intent = Intent(this, ViewOrderActivity::class.java)
                    val bundle2 = Bundle()
                    //输入真实数据
                    bundle2.putString("username", "18071102")
                    bundle2.putString("password", "123456")
                    bundle2.putString("identity", "18071102")
                    bundle2.putString("gender","male" )
                    bundle2.putString("name", "ricardo")
                    bundle2.putString("number", "13552643675")
                    bundle2.putString("mail", "1127676571@qq.com")
                    bundle2.putString("nickname","handsomeBoy" )
                    intent.putExtras(bundle2)
                    startActivity(intent)
                }
                R.id.nav_feedback -> {
                    val intent = Intent(this, FeedbackActivity::class.java)
                    val bundle3 = Bundle()
                    //输入真实数据
                    bundle3.putString("username", "18071102")
                    bundle3.putString("password", "123456")
                    bundle3.putString("identity", "18071102")
                    bundle3.putString("gender","male" )
                    bundle3.putString("name", "ricardo")
                    bundle3.putString("number", "13552643675")
                    bundle3.putString("mail", "1127676571@qq.com")
                    bundle3.putString("nickname","handsomeBoy" )
                    intent.putExtras(bundle3)
                    startActivity(intent)
                }
                R.id.nav_settings -> {
                    val intent = Intent(this , SettingsActivity::class.java)
                    val bundle4 = Bundle()
                    //输入真实数据
                    bundle4.putString("username", "18071102")
                    bundle4.putString("password", "123456")
                    bundle4.putString("identity", "18071102")
                    bundle4.putString("gender","male" )
                    bundle4.putString("name", "ricardo")
                    bundle4.putString("number", "13552643675")
                    bundle4.putString("mail", "1127676571@qq.com")
                    bundle4.putString("nickname","handsomeBoy" )
                    intent.putExtras(bundle4)
                    startActivity(intent)
                }
                R.id.nav_quit -> {
                    val intent = Intent(this,LoginActivity::class.java)
                    startActivity(intent)
                }

            }
            true
        })
    }

    fun startActivity(activity: Activity,model: Bundle) {
        val intent = Intent()
        intent.setClass(activity, HomepageActivity::class.java)
        intent.putExtras(model)
        activity.startActivity(intent)
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