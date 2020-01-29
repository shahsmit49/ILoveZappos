package com.example.myapplication.model

data class AmountModel (
    var high : Double,
    var last : Double,
    var timestamp : Long,
    var  bid : Double,
    var  vwap : Double,
    var  volume : Double,
    var  low : Double,
    var  ask : Double,
    var open : Double
)