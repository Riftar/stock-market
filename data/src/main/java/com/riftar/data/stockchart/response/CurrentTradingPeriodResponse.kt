package com.riftar.data.stockchart.response


import com.google.gson.annotations.SerializedName

data class CurrentTradingPeriodResponse(
    @SerializedName("post")
    val post: TradingPeriodResponse? = null,
    @SerializedName("pre")
    val pre: TradingPeriodResponse? = null,
    @SerializedName("regular")
    val regular: TradingPeriodResponse? = null
)