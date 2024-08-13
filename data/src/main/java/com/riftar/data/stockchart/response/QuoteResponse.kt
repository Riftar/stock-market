package com.riftar.data.stockchart.response


import com.google.gson.annotations.SerializedName

data class QuoteResponse(
    @SerializedName("close")
    val close: List<Double?>? = null,
    @SerializedName("high")
    val high: List<Double?>? = null,
    @SerializedName("low")
    val low: List<Double?>? = null,
    @SerializedName("open")
    val `open`: List<Double?>? = null,
    @SerializedName("volume")
    val volume: List<Int?>? = null
)