package com.example.myapplication.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.wokers.PriceAlertWorker
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity  : AppCompatActivity(){

    private var mDelayHandler: Handler? = null
    private val SPLASHDELAY: Long = 3000 //3 seconds

    private val mRunnable: Runnable = Runnable {

        if (!isFinishing) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

       aminmateIcon()

        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASHDELAY)

    }

    private fun aminmateIcon() {

        val animationScaleUp: Animation = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        //val animationScaleDown: Animation = AnimationUtils.loadAnimation(this, R.anim.scale_up)

        val growShrink = AnimationSet(true)
        growShrink.addAnimation(animationScaleUp)
        //growShrink.addAnimation(animationScaleDown)
        bit_coin_logo.startAnimation(growShrink)
    }

    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }

    override fun onResume() {
        val app_preferences = this.getSharedPreferences(MainActivity.SHARED_PREF, Context.MODE_PRIVATE)
        val price = app_preferences.getString(MainActivity.USER_INPUT_KEY, null)
        PriceAlertWorker.user_input_price = price?:"0.0"
        super.onResume()
    }
}