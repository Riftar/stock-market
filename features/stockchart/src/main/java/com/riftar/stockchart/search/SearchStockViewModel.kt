package com.riftar.stockchart.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.riftar.domain.searchhistory.model.StockHistory
import com.riftar.domain.searchhistory.usecase.SearchStockHistoryByQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest

class SearchStockViewModel(private val searchStockHistoryByQuery: SearchStockHistoryByQuery) :
    ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun getSearchHistory(): Flow<PagingData<StockHistory>> {
        return _searchQuery
            .debounce(1000)
            .flatMapLatest { query ->
                searchStockHistoryByQuery.invoke(query)
            }
            .cachedIn(viewModelScope)
    }

}