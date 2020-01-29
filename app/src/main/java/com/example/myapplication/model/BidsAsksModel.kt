package com.example.myapplication.model

data class BidsAsksModel(val timestamp:Long, val bids: ArrayList<ArrayList<Double>>, val asks: ArrayList<ArrayList<Double>>)