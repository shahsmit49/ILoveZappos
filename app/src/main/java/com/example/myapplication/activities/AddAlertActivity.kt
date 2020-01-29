package com.example.myapplication.activities

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_add_alert.*

class AddAlertActivity : AppCompatActivity() {

    companion object {
        val SHARED_PREF = "shared_preference"
        val USER_INPUT_KEY = "user_bitcoin_price"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_alert)

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
        }

     }
}
