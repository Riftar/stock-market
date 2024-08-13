package com.riftar.data.stockchart.response


import com.google.gson.annotations.SerializedName

data class ChartResponse(
    @SerializedName("error")
    val error: ErrorResponse? = null,
    @SerializedName("result")
    val result: List<ResultResponse?>? = null
)