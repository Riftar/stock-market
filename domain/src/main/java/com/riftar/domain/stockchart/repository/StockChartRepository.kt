package com.riftar.domain.stockchart.repository

import com.riftar.domain.stockchart.model.ChartResult
import kotlinx.coroutines.flow.Flow

interface StockChartRepository {
    fun getStockChart(
        stockCode: String,
        interval: String,
        periodRange: String
    ): Flow<Result<ChartResult>>
}