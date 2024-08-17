package com.riftar.domain.searchhistory.model

data class StockHistory(
    val symbol: String,
    val shortName: String,
    val searchTimeMillis: Long = 0L,
    val close: Double,
    val percentageChange: Double
)
