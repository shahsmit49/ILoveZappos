package com.example.myapplication.wokers

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.myapplication.activities.MainActivity
import com.example.myapplication.api.RetrofitClient
import com.example.myapplication.model.AmountModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PriceAlertWorker (appContext: Context, workerParams: WorkerParameters)
        : Worker(appContext, workerParams) {

    companion object {
        var  context: Context? = null
        var user_input_price :String = "0.0"
    }

    val title : String = "Hey, good news!"
    val desc : String = "Price of bitcoin is less than your bid!"

        override fun doWork(): Result {

            val pref: SharedPreferences = context!!.getSharedPreferences(MainActivity.SHARED_PREF, MODE_PRIVATE)

            val price = pref.getString(MainActivity.USER_INPUT_KEY, null)


            if(user_input_price.equals("0.0") || price == null) {
                Log.d("Price Alert","***User input empty***")
                user_input_price = "0.0"
                // Indicate whether the task finished with failure with the Result
                return Result.failure()
            }


            // Check for bitcoin price
            // get bid price
            getBitcoinAmount(user_input_price.toString().toDouble())


            // Indicate whether the task finished successfully with the Result
            return Result.success()
        }

    private fun notifyUser() {

        val intent = Intent(context, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        // Create the TaskStackBuilder
        val pendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
            // Add the intent, which inflates the back stack
            addNextIntentWithParentStack(intent)
            // Get the PendingIntent containing the entire back stack
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        //val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "bitcoin_price",
                "Bitcoin notification",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            manager.createNotificationChannel(channel)
        }

        var alarmSound: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val builder =
            NotificationCompat.Builder(applicationContext, "bitcoin_price")
                .setContentTitle(title)
                .setContentText(desc)
                .setSmallIcon(R.mipmap.sym_def_app_icon)
                .setContentIntent(pendingIntent)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setSound(alarmSound)

        manager.notify(1, builder.build())
    }


    private fun getBitcoinAmount(user_input_price : Double) {

        RetrofitClient.instance.get_ticker_hour()
            .enqueue(object : Callback<AmountModel> {

                override fun onFailure(call: Call<AmountModel>, t: Throwable) {
                    Log.d(t.message,"***Failed***")
                    return
                }

                override fun onResponse(call: Call<AmountModel>, response: Response<AmountModel>) {

                    if (!response.isSuccessful || response.body() == null )
                        return

                    Log.d(response.body()!!.bid.toString()," ***user input:- "+user_input_price.toString())

                    if(response.body()!!.bid > user_input_price.toString().toDouble())
                        return

                    Log.d(response.isSuccessful.toString(),"***Success***")

                    //show notification if bitcoin price is less than the amount entered by user
                    notifyUser()
                }
            })
    }
}
