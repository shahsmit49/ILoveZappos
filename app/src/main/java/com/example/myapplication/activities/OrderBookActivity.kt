package com.example.myapplication.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.R
import com.example.myapplication.adapter.OrderBookViewAdapter
import com.example.myapplication.api.RetrofitClient
import com.example.myapplication.model.BidsAsksModel
import kotlinx.android.synthetic.main.order_book_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OrderBookActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_book_activity)

        // SwipeRefreshLayout
        swipe_recycle_view.setOnRefreshListener(this)
        swipe_recycle_view.setColorSchemeResources(
            R.color.colorGreen,
            R.color.colorRed,
            R.color.colorPrimary
        )

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipe_recycle_view.post(Runnable {
            // Fetching data from server
            loadData()

        })

    }

    /**
     * This method is called when swipe refresh is pulled down
     */
    override fun onRefresh() {
        loadData()
    }

    private fun loadData() {

        swipe_recycle_view.isRefreshing = true

        RetrofitClient.instance.getBidsAndAsks()
            .enqueue(object : Callback<BidsAsksModel> {

                override fun onFailure(call: Call<BidsAsksModel>, t: Throwable) {
                    // Stopping swipe refresh
                    swipe_recycle_view.isRefreshing = false
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse( call: Call<BidsAsksModel>,  response: Response<BidsAsksModel> ) {

                    if (!response.isSuccessful || response.body() == null )
                        return

                    recycler_view.apply {
                        // Creates a vertical Layout Manager
                        layoutManager = LinearLayoutManager(this@OrderBookActivity)
                        // Access the RecyclerView Adapter and load the data into it
                        adapter =  OrderBookViewAdapter(response.body()!!.bids, response.body()!!.asks, this@OrderBookActivity)
                    }

                    // Stopping swipe refresh
                    swipe_recycle_view.isRefreshing = false

                }
            })
    }

}
