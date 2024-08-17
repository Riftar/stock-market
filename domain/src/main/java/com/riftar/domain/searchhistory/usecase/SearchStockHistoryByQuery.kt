package com.riftar.domain.searchhistory.usecase

import com.riftar.domain.searchhistory.repository.SearchStockRepository

class SearchStockHistoryByQuery(private val repository: SearchStockRepository) {
    operator fun invoke(query: String) = repository.getListUserByQuery(query)
}