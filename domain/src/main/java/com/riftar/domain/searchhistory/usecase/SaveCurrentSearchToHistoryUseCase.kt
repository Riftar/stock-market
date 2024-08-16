package com.riftar.domain.searchhistory.usecase

import com.riftar.domain.searchhistory.mapper.toStockHistory
import com.riftar.domain.searchhistory.repository.SearchStockRepository
import com.riftar.domain.stockchart.model.ChartResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveCurrentSearchToHistoryUseCase(private val repository: SearchStockRepository) {
    operator fun invoke(chartResult: ChartResult): Flow<Result<Unit>> = flow {
        try {
            val stockHistory = chartResult.toStockHistory()
            repository.saveCurrentSearchToHistory(stockHistory).collect { result ->
                emit(result)
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}