package com.riftar.stockchart.chart

import android.content.Context
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.riftar.common.helper.orZero
import com.riftar.domain.stockchart.model.ChartResult
import com.riftar.stockchart.R
import com.riftar.stockchart.chart.ChartFormatter.dateFormatter
import com.riftar.stockchart.chart.ChartFormatter.dollarFormatter

object ChartHelper {

    fun LineChart.setupChartLayout(chartResult: ChartResult) {
        if (chartResult.indicators.quote.getOrNull(0)?.close.orEmpty().isEmpty()) return
        val limit =
            createLimitLine(
                this.context,
                chartResult.indicators.quote.getOrNull(0)?.close?.lastOrNull().orZero()
            )
        this.apply {
            setBackgroundColor(
                ContextCompat.getColor(
                    this.context,
                    com.riftar.common.R.color.background_solid
                )
            )
            description.isEnabled = false
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)
            setDrawMarkers(false)
            legend.isEnabled = false
            axisLeft.apply {
                axisMinimum = chartResult.indicators.quote.getOrNull(0)?.close?.min()
                    .orZero().toFloat()
                setDrawGridLines(false)
                textSize = 12f
                labelCount = 8
                textColor = ContextCompat.getColor(
                    this@setupChartLayout.context,
                    com.riftar.common.R.color.text_color_primary
                )
                addLimitLine(limit)
                setDrawLimitLinesBehindData(true)
                valueFormatter = dollarFormatter
                spaceMin = 1f
                setClipValuesToContent(false)
            }
            axisRight.apply {
                isEnabled = false
                setDrawGridLines(false)
            }
            xAxis.apply {
                //disable grid line in background
                setDrawGridLines(false)
                position = XAxis.XAxisPosition.BOTTOM
                valueFormatter = dateFormatter
                labelCount = 6
                textSize = 12f
                textColor = ContextCompat.getColor(
                    this@setupChartLayout.context,
                    com.riftar.common.R.color.text_color_primary
                )
            }
            extraBottomOffset = 4f
            setDrawMarkers(true)
            marker = CustomMarkerView(this.context, R.layout.layout_marker_view)
        }
    }

    private fun createLimitLine(context: Context, last: Double?): LimitLine {
        val limit = LimitLine(last.orZero().toFloat(), "")
        return limit.apply {
            lineWidth = 2f
            lineColor = ContextCompat.getColor(
                context,
                com.riftar.common.R.color.text_color_gray
            )
            enableDashedLine(10f, 10f, 0f)
        }
    }

    fun createLineDataSet(context: Context, chartData: List<Entry>): LineData {
        val lineDataSet = LineDataSet(chartData, "")
        lineDataSet.apply {
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            color = ContextCompat.getColor(
                context,
                com.riftar.common.R.color.green_profit
            )
            highLightColor = ContextCompat.getColor(
                context,
                com.riftar.common.R.color.background_invert_gray
            )
            setDrawValues(false)
            setDrawCircles(false)
            lineWidth = 2f
            setDrawFilled(true)
            fillDrawable = AppCompatResources.getDrawable(
                context,
                R.drawable.bg_chart_green_gradient
            )
        }

        return LineData(lineDataSet)
    }
}