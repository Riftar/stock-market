package com.riftar.data.stockchart.response


import com.google.gson.annotations.SerializedName

data class StockChartResponse(
    @SerializedName("chart")
    val chart: ChartResponse? = null
)