package com.riftar.data.stockchart.response


import com.google.gson.annotations.SerializedName

data class IndicatorsResponse(
    @SerializedName("quote")
    val quote: List<QuoteResponse?>? = null
)