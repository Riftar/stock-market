package com.riftar.domain.stockchart.usecase

import com.riftar.domain.stockchart.repository.StockChartRepository

class GetStockChartUseCase(private val repository: StockChartRepository) {
    operator fun invoke(stockCode: String) = repository.getStockChart(stockCode)
}