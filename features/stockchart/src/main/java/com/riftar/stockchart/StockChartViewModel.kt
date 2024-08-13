package com.riftar.stockchart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riftar.domain.stockchart.model.ChartResult
import com.riftar.domain.stockchart.usecase.GetStockChartUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StockChartViewModel(private val getStockChartUseCase: GetStockChartUseCase) : ViewModel() {
    private val _stockChartState = MutableStateFlow<StockChartState>(StockChartState.Loading)
    val stockChartState: StateFlow<StockChartState> = _stockChartState.asStateFlow()

    fun getStockChartData(stockCode: String) {
        viewModelScope.launch {
            _stockChartState.value = StockChartState.Loading
            getStockChartUseCase.invoke(stockCode)
                .collect { result ->
                    result.onSuccess { data ->
                        _stockChartState.value = StockChartState.Success(data)
                    }.onFailure { exception ->
                        Log.d("Rifqi-test", "getStockChartData: $exception")
                        _stockChartState.value =
                            StockChartState.Error(exception.message ?: "Unknown error occurred")
                    }
                }
        }
    }
}

sealed class StockChartState {
    data object Loading : StockChartState()
    data class Success(val charResult: ChartResult) : StockChartState()
    data class Error(val message: String) : StockChartState()
}