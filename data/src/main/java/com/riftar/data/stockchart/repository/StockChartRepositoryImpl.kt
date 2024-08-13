package com.riftar.data.stockchart.repository

import android.util.Log
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
        try {
            val response = api.getStockChart(stockCode)
            val result = response.body()?.chart

            Log.d("Rifqi-test", "result $result")
            if (response.isSuccessful && result?.result != null) {
                // TODO check
                emit(Result.success(result.result.getOrNull(0).toDomainModel()))
            } else {
                Log.d("Rifqi-test", "response code: ${response.code()}")
                emit(Result.failure(Exception(result?.error?.description ?: "Unknown error")))
            }
        } catch (e: Exception) {

            Log.d("Rifqi-test", "catch api: $e")
            // 3. Emit error to show error immediately, but don't stop the flow
            emit(Result.failure(e))
        }
    }.retryWhen { cause, attempt ->
        // 4. Retry the flow when IOException is thrown and the attempt count is less than 3
        if (cause is IOException && attempt < 3) {
            val delay = 1000 * (attempt + 1)
            delay(delay)
            return@retryWhen true
        } else {
            Log.d("Rifqi-test", "retry called")
            emit(Result.failure(cause))
            return@retryWhen false
        }
    }.catch { e ->

        Log.d("Rifqi-test", "catch end flow: $e")
        emit(Result.failure(e))
    }
}