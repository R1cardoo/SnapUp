package com.example.snapup_android.viewOrder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.snapup_android.R
import kotlinx.android.synthetic.main.activity_train_info.*
import java.util.ArrayList

class TrainInfo : AppCompatActivity() {

        private val TRAIN_ID = "train_id"
        private val STARTING_STATION = ""
        private val TERMINUS = ""
        val STOPOVERS : List<String> = ArrayList()              //经停站



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_train_info)

        val trainId = intent.getStringExtra(TRAIN_ID) ?: ""
        val startingStation = intent.getStringExtra(STARTING_STATION)?: "i am starting station"
        val terminus = intent.getStringExtra(TERMINUS)?: "i am terminus"
        //val stopovers = intent.getStringArrayListExtra()


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        collapsingToolbar.title = trainId         //名字
        Glide.with(this).load(trainId).into(fruitImageView)
        fruitContentText.text = generateFruitContent(terminus)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun generateFruitContent(fruitName: String) = fruitName.repeat(500)
}