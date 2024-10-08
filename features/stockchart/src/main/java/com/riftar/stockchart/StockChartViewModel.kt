package com.riftar.stockchart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riftar.domain.searchhistory.usecase.SaveCurrentSearchToHistoryUseCase
import com.riftar.domain.stockchart.model.ChartResult
import com.riftar.domain.stockchart.usecase.GetStockChartUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StockChartViewModel(
    private val getStockChartUseCase: GetStockChartUseCase,
    private val saveCurrentSearchToHistoryUseCase: SaveCurrentSearchToHistoryUseCase
) : ViewModel() {
    private val _stockChartState = MutableStateFlow<StockChartState>(StockChartState.Initial)
    val stockChartState: StateFlow<StockChartState> = _stockChartState.asStateFlow()

    private val _periodState = MutableStateFlow("1d")
    private val periodState = _periodState.asStateFlow()

    private var currentStockCode: String? = null
    private var currentSearchTimeMillis: Long = 0

    fun setStockCode(stockCode: String, searchTimeMillis: Long) {
        currentStockCode = stockCode
        currentSearchTimeMillis = searchTimeMillis
        getStockChartData()
    }

    fun setPeriodState(period: String) {
        _periodState.value = period
    }

    init {
        viewModelScope.launch {
            periodState.collect {
                getStockChartData()
            }
        }
    }

    private fun getStockChartData() {
        val stockCode = currentStockCode ?: return
        val period = _periodState.value

        viewModelScope.launch {
            _stockChartState.value = StockChartState.Loading
            getStockChartUseCase.invoke(stockCode, period)
                .collect { result ->
                    result.onSuccess { data ->
                        _stockChartState.value = StockChartState.Success(data)
                        saveCurrentSearchToHistory(data, currentSearchTimeMillis)
                    }.onFailure { exception ->
                        _stockChartState.value =
                            StockChartState.Error(exception.message ?: "Unknown error occurred")
                    }
                }
        }
    }

    private fun saveCurrentSearchToHistory(chartResult: ChartResult, searchTimeMillis: Long) {
        viewModelScope.launch {
            saveCurrentSearchToHistoryUseCase.invoke(chartResult, searchTimeMillis)
                .collect { result ->
                    result.onSuccess {
                        // no need to show anything
                    }.onFailure {
                        _stockChartState.value =
                            StockChartState.Error(it.message ?: "Unknown error occurred")
                    }
                }
        }
    }
}

sealed class StockChartState {
    data object Initial : StockChartState()
    data object Loading : StockChartState()
    data class Success(val chartResult: ChartResult) : StockChartState()
    data class Error(val message: String) : StockChartState()
}