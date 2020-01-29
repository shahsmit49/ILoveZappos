package com.example.myapplication.activities

import android.graphics.Color
import android.graphics.Color.WHITE
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.api.RetrofitClient
import com.example.myapplication.model.TransactionHistoryModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_view_graph.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ViewPriceHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_graph)

        title = "LineChartTime"

        RetrofitClient.instance.getTransactionHistory()
            .enqueue(object:Callback<List<TransactionHistoryModel>>{

                override fun onFailure(call: Call<List<TransactionHistoryModel>>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<List<TransactionHistoryModel>>,
                    response: Response<List<TransactionHistoryModel>>
                ) {


                    if(!response.isSuccessful)
                        return

                    var transactionHistory = response.body()

                    if(transactionHistory.isNullOrEmpty())
                        return

                    val chart = line_chart
                    setChartProperties(chart)

                    var dataValues: ArrayList<Entry> = ArrayList()

                    for (history in transactionHistory){
                        dataValues.add(Entry((history.date*1000).toFloat(), history.price))
                    }

                    val lineDataSet1 = LineDataSet(dataValues,"Price history over time")
                    lineDataSet1.color = WHITE
                    lineDataSet1.setDrawCircles(true)
                    lineDataSet1.setCircleColor(Color.YELLOW)
                    lineDataSet1.setDrawCircleHole(true)
                    lineDataSet1.circleHoleColor =WHITE

                    val lineData = LineData(lineDataSet1)

                    chart.data = lineData
                    chart.invalidate()
                }

            })
    }

    private fun setChartProperties(chart:LineChart) {

        // no description text
        chart.description.isEnabled = false

        // enable touch gestures
        chart.setTouchEnabled(true)
        chart.setPinchZoom(true)
        chart.isScaleYEnabled = false
       // chart.dragDecelerationFrictionCoef = 0.9f

        // enable scaling and dragging
        chart.isDragEnabled = true
        //chart.setDrawGridBackground(false)
        chart.isHighlightPerDragEnabled = true
        // set an alternative background color
        chart.setBackgroundColor(Color.BLACK)
        chart.setNoDataText("No data")
        chart.setNoDataTextColor(WHITE)

        //chart.setDrawGridBackground(true)
        chart.setBorderColor(WHITE)
        chart.setDrawBorders(true)

        //animate x
        chart.animateX(3000)

        //Legend
        val legend = chart.legend
        legend.isEnabled = true
        legend.form = Legend.LegendForm.LINE
        legend.xEntrySpace = 25f
        legend.textColor = WHITE

        val xAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.TOP
        //xAxis.typeface = tfLight
        xAxis.textSize = 10f
        xAxis.textColor = WHITE
        xAxis.setDrawAxisLine(false)
        xAxis.setDrawGridLines(true)
        xAxis.gridColor = Color.YELLOW
        xAxis.setCenterAxisLabels(true)

        xAxis.valueFormatter = object : ValueFormatter() {
            private val mFormat: SimpleDateFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
            override fun getFormattedValue(value: Float): String {
                return mFormat.format(value.toLong())
            }
        }

        val leftAxis = chart.axisLeft
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        //leftAxis.typeface = tfLight
        leftAxis.textColor = WHITE
        leftAxis.setDrawGridLines(true)
        leftAxis.gridColor = Color.YELLOW
        leftAxis.granularity = 1f
        //leftAxis.axisMinimum = 0f

        val rightAxis = chart.axisRight
        rightAxis.isEnabled = false
    }
}
