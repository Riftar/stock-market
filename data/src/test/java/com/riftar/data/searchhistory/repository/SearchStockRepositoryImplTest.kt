package com.riftar.data.searchhistory.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.riftar.data.searchhistory.room.dao.SearchHistoryDao
import com.riftar.data.searchhistory.room.entity.SearchHistoryEntity
import com.riftar.domain.searchhistory.model.StockHistory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchStockRepositoryImplTest {

    @Mock
    private lateinit var dao: SearchHistoryDao

    private lateinit var repository: SearchStockRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = SearchStockRepositoryImpl(dao)
    }

    @Test
    fun `getListUserByQuery returns paging data`() = runTest {
        val query = "AAPL"
        val pagingSource = mock(PagingSource::class.java) as PagingSource<Int, SearchHistoryEntity>

        `when`(dao.getSearchHistoryByQuery(query)).thenReturn(pagingSource)

        val result = repository.getListUserByQuery(query).first()

        assertTrue(result is PagingData<StockHistory>)
        verify(dao).getSearchHistoryByQuery(query)
    }

    @Test
    fun `saveCurrentSearchToHistory emits success when insert succeeds`() = runTest {
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
        `when`(dao.insertAndGetSearchHistoryById(stockHistoryEntity)).thenReturn(stockHistoryEntity)

        val result = repository.saveCurrentSearchToHistory(stockHistory).first()
        assertTrue(result.isSuccess)
        verify(dao).insertAndGetSearchHistoryById(stockHistoryEntity)
    }

    @Test
    fun `saveCurrentSearchToHistory emits failure when insert fails`() = runTest {
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

        `when`(dao.insertAndGetSearchHistoryById(stockHistoryEntity)).thenReturn(null)

        val result = repository.saveCurrentSearchToHistory(stockHistory).first()

        assertTrue(result.isFailure)
        assertEquals("Failed to save search history", result.exceptionOrNull()?.message)
        verify(dao).insertAndGetSearchHistoryById(stockHistoryEntity)
    }

    @Test
    fun `saveCurrentSearchToHistory emits failure when insert returning wrong data`() = runTest {
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

        `when`(dao.insertAndGetSearchHistoryById(stockHistoryEntity)).thenReturn(
            stockHistoryEntity.copy(
                percentageChange = 0.11
            )
        )

        val result = repository.saveCurrentSearchToHistory(stockHistory).first()

        assertTrue(result.isFailure)
        assertEquals("Failed to save search history", result.exceptionOrNull()?.message)
        verify(dao).insertAndGetSearchHistoryById(stockHistoryEntity)
    }

    @Test
    fun `saveCurrentSearchToHistory emits failure when exception occurs`() = runTest {
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

        `when`(dao.insertAndGetSearchHistoryById(stockHistoryEntity)).thenThrow(RuntimeException("Database error"))

        val result = repository.saveCurrentSearchToHistory(stockHistory)

        assertTrue(result.first().isFailure)
        assertEquals("Database error", result.first().exceptionOrNull()?.message)
        // verify(dao).insertAndGetSearchHistoryById(stockHistoryEntity)
        // running 2 times in test, but on the actual usage only run once
        verify(dao, atLeastOnce()).insertAndGetSearchHistoryById(stockHistoryEntity)
    }
}