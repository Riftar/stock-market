package com.riftar.data.searchhistory.mapper

import com.riftar.data.searchhistory.room.entity.SearchHistoryEntity
import com.riftar.domain.searchhistory.model.StockHistory
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SearchStockMapperKtTest {

    @Test
    fun testToDomainModel() = runTest {
        val stockHistory = StockHistory(
            symbol = "AAPL",
            shortName = "Apple Inc.",
            close = 122.13,
            percentageChange = 14.1,
            searchTimeMillis = 1723798058816
        )
        val stockHistoryEntity = SearchHistoryEntity(
            symbol = stockHistory.symbol,
            shortName = stockHistory.shortName,
            close = stockHistory.close,
            percentageChange = stockHistory.percentageChange,
            searchTimeMillis = stockHistory.searchTimeMillis
        )

        assert(stockHistoryEntity.toDomainModel() == stockHistory)
    }

    @Test
    fun testToEntityModel() = runTest {
        val stockHistory = StockHistory(
            symbol = "AAPL",
            shortName = "Apple Inc.",
            close = 122.13,
            percentageChange = 14.1,
            searchTimeMillis = 1723798058816
        )
        val stockHistoryEntity = SearchHistoryEntity(
            symbol = stockHistory.symbol,
            shortName = stockHistory.shortName,
            close = stockHistory.close,
            percentageChange = stockHistory.percentageChange,
            searchTimeMillis = stockHistory.searchTimeMillis
        )

        assert(stockHistory.toEntityModel() == stockHistoryEntity)
    }
}