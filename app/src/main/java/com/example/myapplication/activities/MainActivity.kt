package com.example.myapplication.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.myapplication.R
import com.example.myapplication.wokers.PriceAlertWorker
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PriceAlertWorker.context = this@MainActivity
        val periodicWorkRequest = PeriodicWorkRequest.Builder(PriceAlertWorker::class.java, 1, TimeUnit.MINUTES).build()
        WorkManager.getInstance().enqueue(periodicWorkRequest)

        show_orders.setOnClickListener {
            // Handler code here.
            val intent = Intent(this, OrderBookActivity::class.java)
            startActivity(intent)
        }

        show_graph.setOnClickListener {
            // Handler code here.
            val intent = Intent(this, ViewPriceHistoryActivity::class.java)
            startActivity(intent)
        }

        add_alert.setOnClickListener {
            // Handler code here.
            val intent = Intent(this, AddAlertActivity::class.java)
            startActivity(intent)
        }
    }

}
