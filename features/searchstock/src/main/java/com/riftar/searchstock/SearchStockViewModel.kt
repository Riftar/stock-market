package com.riftar.searchstock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.riftar.domain.searchhistory.model.StockHistory
import com.riftar.domain.searchhistory.usecase.SearchStockHistoryByQuery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest

class SearchStockViewModel(private val searchStockHistoryByQuery: SearchStockHistoryByQuery) :
    ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()
    private val _searchStockState =
        MutableStateFlow<SearchStockState>(SearchStockState.Loading(true))
    val searchStockState = _searchStockState.asStateFlow()

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    fun getSearchHistory(): Flow<PagingData<StockHistory>> {
        return _searchQuery
            .debounce(1000)
            .flatMapLatest { query ->
                searchStockHistoryByQuery.invoke(query)
            }
            .cachedIn(viewModelScope)
    }

    fun setEmptyViewVisibility(loadState: CombinedLoadStates, itemCount: Int?) {
        _searchStockState.value = SearchStockState.EmptyLayoutVisibility(
            loadState.source.refresh is LoadState.NotLoading && itemCount == 0
        )
    }

    fun setProgressBarVisibility(isLoading: Boolean) {
        _searchStockState.value = SearchStockState.Loading(isLoading)
    }

    fun onSearchClicked(query: String) {
        if (query.isEmpty()) {
            _searchStockState.value = SearchStockState.Error("Input can't be empty")
        } else {
            _searchStockState.value = SearchStockState.Search(query)
        }
    }

}

sealed class SearchStockState {
    data object Initial : SearchStockState()
    data class Loading(val isLoading: Boolean) : SearchStockState()
    data class EmptyLayoutVisibility(val isVisible: Boolean) : SearchStockState()
    data class Error(val message: String) : SearchStockState()
    data class Search(val query: String) : SearchStockState()
}

