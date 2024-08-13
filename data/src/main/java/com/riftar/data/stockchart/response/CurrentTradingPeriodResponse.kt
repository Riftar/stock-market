package com.riftar.data.stockchart.response


import com.google.gson.annotations.SerializedName

data class CurrentTradingPeriodResponse(
    @SerializedName("post")
    val post: PostResponse? = null,
    @SerializedName("pre")
    val pre: PreResponse? = null,
    @SerializedName("regular")
    val regular: RegularResponse? = null
)