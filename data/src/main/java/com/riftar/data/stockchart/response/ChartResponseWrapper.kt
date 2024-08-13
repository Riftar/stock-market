package com.riftar.data.stockchart.response

import com.google.gson.annotations.SerializedName


data class ChartResponseWrapper(
    @SerializedName("chart")
    val chart: ChartResponse? = null,
)