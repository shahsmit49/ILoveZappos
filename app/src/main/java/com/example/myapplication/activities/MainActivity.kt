package com.example.myapplication.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.myapplication.R
import com.example.myapplication.wokers.PriceAlertWorker
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    companion object {
        var menuLocal: Menu? = null
        const val SHARED_PREF = "shared_preference"
        const val USER_INPUT_KEY = "user_bitcoin_price"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PriceAlertWorker.context = this@MainActivity
        val periodicWorkRequest = PeriodicWorkRequest.Builder(PriceAlertWorker::class.java, 1, TimeUnit.HOURS).build()
        WorkManager.getInstance(this).enqueue(periodicWorkRequest)

        save_button.setOnClickListener {

            val sharedPreferences: SharedPreferences = this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)

            val amount = bitcoin_price.text.toString()

            val editor : SharedPreferences.Editor =  sharedPreferences.edit()

            editor.remove(USER_INPUT_KEY)

            editor.clear()

            editor.putString(USER_INPUT_KEY, amount)

            editor.commit()

            bitcoin_price.text.clear()

            Toast.makeText(this, "Saved!" , Toast.LENGTH_SHORT).show()

            val item: MenuItem = menuLocal!!.findItem(R.id.user_input)

            item.title = amount
        }

        show_orders.setOnClickListener {
            // Handler code here
            val intent = Intent(this, OrderBookActivity::class.java)
            startActivity(intent)
        }

        show_graph.setOnClickListener {
            // Handler code here
            val intent = Intent(this, ViewPriceHistoryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { // Inflate the menu; this adds items to the action bar if it is present.
        menuLocal = menu
        menuInflater.inflate(R.menu.price_menu, menu)
        val item: MenuItem = menu!!.findItem(R.id.user_input)
        item.title = PriceAlertWorker.user_input_price
        return true
    }

    override fun onResume() {
        val app_preferences = this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val price = app_preferences.getString(USER_INPUT_KEY, null)
        PriceAlertWorker.user_input_price = price?:"0.0"
        super.onResume()
    }
}
