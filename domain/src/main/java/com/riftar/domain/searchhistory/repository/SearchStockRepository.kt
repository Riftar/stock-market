package com.riftar.domain.searchhistory.repository

import androidx.paging.PagingData
import com.riftar.domain.searchhistory.model.StockHistory
import kotlinx.coroutines.flow.Flow

interface SearchStockRepository {
    fun getListUserByQuery(query: String): Flow<PagingData<StockHistory>>
    fun saveCurrentSearchToHistory(stockHistory: StockHistory): Flow<Result<Unit>>
}