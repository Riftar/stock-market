package com.riftar.data.stockchart.response


import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("code")
    val code: String? = null,
    @SerializedName("description")
    val description: String? = null
)