package com.riftar.data.stockchart.response


import com.google.gson.annotations.SerializedName

data class ResultResponse(
    @SerializedName("indicators")
    val indicators: IndicatorsResponse? = null,
    @SerializedName("meta")
    val meta: MetaResponse? = null,
    @SerializedName("timestamp")
    val timestamp: List<Int?>? = null
)