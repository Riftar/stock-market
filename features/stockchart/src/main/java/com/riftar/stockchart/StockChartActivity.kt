package com.riftar.stockchart

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.riftar.domain.stockchart.model.ChartResult
import com.riftar.stockchart.chart.CustomMarkerView
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Locale


class StockChartActivity : AppCompatActivity() {
    private val viewModel: StockChartViewModel by viewModel()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_stock_chart)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        showData(null)
        val btn = findViewById<Button>(R.id.btnSearch)
        btn.setOnClickListener {
            val etInput = findViewById<EditText>(R.id.etInput)
            viewModel.getStockChartData(etInput.text.toString())
        }
        lifecycleScope.launch {
            val tvResult = findViewById<TextView>(R.id.tvResult)
            viewModel.stockChartState.collect {
                when (it) {
                    is StockChartState.Error -> {

                        tvResult.text = "Error ${it.message}"
                    }

                    StockChartState.Loading -> {

                        tvResult.text = "Loading"
                    }

                    is StockChartState.Success -> {

                        tvResult.text = it.charResult.toString()
                        showData(it.charResult)
                    }
                }
            }
        }
    }

    private fun showData(chartResult: ChartResult?) {
        val chart = findViewById<LineChart>(R.id.chart)
        chart.apply {
            setBackgroundColor(Color.WHITE)
            description.isEnabled = false
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)
            setDrawMarkers(false)
            legend.isEnabled = false
            axisLeft.setDrawGridLines(false)
            axisRight.apply {
                isEnabled = false
                setDrawGridLines(false)
            }
            xAxis.apply {
                //disable grid line in background
                setDrawGridLines(false)
                position = XAxis.XAxisPosition.BOTTOM
                valueFormatter = formatterX
                labelCount = 8
            }
            setDrawMarkers(true)
            marker = CustomMarkerView(this@StockChartActivity, R.layout.layout_marker_view)
        }

        val chartData = chartResult?.timestamp?.mapIndexed { index, i ->
            Entry(i.toFloat(), chartResult.indicators.quote[0].close[index].toFloat())
        }

        val lineDataSet = LineDataSet(chartData, "")
        lineDataSet.apply {
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            color = Color.GREEN
            highLightColor = Color.GRAY
            setDrawValues(false)
            setDrawCircles(false)
            lineWidth = 2f
            setDrawFilled(true)
            fillDrawable = AppCompatResources.getDrawable(
                this@StockChartActivity,
                R.drawable.bg_chart_green_gradient
            )
        }

        val lineData = LineData(lineDataSet)

        chart.data = lineData
    }


    fun Float.formatFloatToHours(format: String = "HH:mm"): String {
        return SimpleDateFormat(format, Locale("id", "ID")).format(this)
    }

    fun Float.formatFloatToDate(format: String = "MMM dd"): String {
        return SimpleDateFormat(format, Locale("id", "ID")).format(this)
    }

    private val formatterX = object : IndexAxisValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return value.formatFloatToHours()
        }
    }
}