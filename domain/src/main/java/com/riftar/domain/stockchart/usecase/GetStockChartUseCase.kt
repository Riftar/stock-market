package com.riftar.domain.stockchart.usecase

import com.riftar.domain.stockchart.model.ChartResult
import com.riftar.domain.stockchart.repository.StockChartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetStockChartUseCase(private val repository: StockChartRepository) {
    operator fun invoke(stockCode: String, period: String): Flow<Result<ChartResult>> = flow {
        val interval = calculateInterval(period)
        repository.getStockChart(stockCode, interval, period).collect {
            emit(it)
        }
    }.catch { e ->
        emit(Result.failure(e))
    }

    private fun calculateInterval(period: String): String {
        return when (period) {
            "1d" -> "2m"
            "5d" -> "15m"
            "1mo" -> "1h"
            "6mo" -> "1d"
            "ytd" -> "5d"
            else -> throw IllegalArgumentException("Invalid period: $period")
        }
    }

}