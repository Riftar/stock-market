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
    private val _stockChartState = MutableStateFlow<StockChartState>(StockChartState.Loading)
    val stockChartState: StateFlow<StockChartState> = _stockChartState.asStateFlow()
    private val _saveHistoryState =
        MutableStateFlow<SaveStockHistoryState>(SaveStockHistoryState.Initial)
    val saveHistoryState: StateFlow<SaveStockHistoryState> = _saveHistoryState.asStateFlow()

    fun getStockChartData(stockCode: String, searchTimeMillis: Long) {
        viewModelScope.launch {
            _stockChartState.value = StockChartState.Loading
            getStockChartUseCase.invoke(stockCode)
                .collect { result ->
                    result.onSuccess { data ->
                        _stockChartState.value = StockChartState.Success(data)
                        saveCurrentSearchToHistory(data, searchTimeMillis)
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
                        _saveHistoryState.value = SaveStockHistoryState.Success
                    }.onFailure {
                        _saveHistoryState.value =
                            SaveStockHistoryState.Error(it.message ?: "Unknown error occurred")
                    }
                }
        }
    }
}

sealed class StockChartState {
    data object Loading : StockChartState()
    data class Success(val chartResult: ChartResult) : StockChartState()
    data class Error(val message: String) : StockChartState()
}


sealed class SaveStockHistoryState {
    data object Initial : SaveStockHistoryState()
    data object Success : SaveStockHistoryState()
    data class Error(val message: String) : SaveStockHistoryState()
}