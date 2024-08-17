package com.riftar.domain.searchhistory.mapper

import com.riftar.domain.searchhistory.model.StockHistory
import com.riftar.domain.stockchart.model.ChartResult


fun ChartResult.toStockHistory(searchTimeMillis: Long) = StockHistory(
    symbol = this.meta.symbol,
    shortName = this.meta.shortName,
    searchTimeMillis = searchTimeMillis,
    close = this.indicators.quote.getOrNull(0)?.close?.lastOrNull() ?: 0.0,
    percentageChange = this.calculatePercentageChange()
)

fun ChartResult.calculateGainOrLoss(): Double {
    val diff = this.meta.regularMarketPrice - this.meta.previousClose
    return diff
}

fun ChartResult.calculatePercentageChange(): Double {
    val percentageChange = this.calculateGainOrLoss() / this.meta.previousClose * 100
    return percentageChange
}