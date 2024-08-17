package com.riftar.data.stockchart.repository

import com.riftar.data.stockchart.api.StockChartAPI
import com.riftar.data.stockchart.mapper.toDomainModel
import com.riftar.domain.stockchart.model.ChartResult
import com.riftar.domain.stockchart.repository.StockChartRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retryWhen
import okio.IOException

class StockChartRepositoryImpl(private val api: StockChartAPI) : StockChartRepository {
    override fun getStockChart(stockCode: String): Flow<Result<ChartResult>> = flow {
        // 1. Call the API and get the response
        val response = api.getStockChart(stockCode)
        val result = response.body()?.chart

        // 2. Check if the response is successful and the result is not null
        if (response.isSuccessful && result?.result != null) {
            emit(Result.success(result.result.getOrNull(0).toDomainModel()))
        } else {
            val errorMessage = if (response.code() == 404) {
                "No data found, symbol may be delisted"
            } else "Unknown error"
            emit(Result.failure(Exception(result?.error?.description ?: errorMessage)))
        }
    }.retryWhen { cause, attempt ->
        // 4. Retry the flow when IOException is thrown and the attempt count is less than 3
        if (cause is IOException && attempt < 3) {
            val delay = 1000 * (attempt + 1)
            delay(delay)
            return@retryWhen true
        } else {
            emit(Result.failure(cause))
            return@retryWhen false
        }
    }.catch { e ->
        emit(Result.failure(e))
    }
}