package com.example.myapplication.api

import com.example.myapplication.model.AmountModel
import com.example.myapplication.model.BidsAsksModel
import com.example.myapplication.model.TransactionHistoryModel
import retrofit2.Call
import retrofit2.http.GET

interface Api {

    @GET("api/v2/transactions/btcusd/")
    fun getTransactionHistory(): Call<List<TransactionHistoryModel>>

    @GET("api/v2/order_book/btcusd/")
    fun getBidsAndAsks(): Call<BidsAsksModel>

    @GET("api/v2/ticker_hour/btcusd/")
    fun get_ticker_hour(): Call<AmountModel>
}