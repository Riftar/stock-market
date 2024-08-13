package com.riftar.data.stockchart.response


import com.google.gson.annotations.SerializedName

data class PreResponse(
    @SerializedName("end")
    val end: Int? = null,
    @SerializedName("gmtoffset")
    val gmtoffset: Int? = null,
    @SerializedName("start")
    val start: Int? = null,
    @SerializedName("timezone")
    val timezone: String? = null
)