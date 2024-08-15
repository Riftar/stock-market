package com.riftar.stockchart.chart

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.riftar.common.helper.unixTimestampToDate

object ChartFormatter {
    val dollarFormatter = object : IndexAxisValueFormatter() {

        override fun getFormattedValue(value: Float): String {
            return "$${value.toInt()}"
        }

        override fun setValues(values: Array<out String>?) {
            super.setValues(values)
        }
    }

    val dateFormatter = object : IndexAxisValueFormatter() {

        override fun getFormattedValue(value: Float): String {
            return value.toLong().unixTimestampToDate()
        }

        override fun setValues(values: Array<out String>?) {
            super.setValues(values)
        }
    }
}