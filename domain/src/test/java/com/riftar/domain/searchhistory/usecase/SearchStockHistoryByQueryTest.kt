package com.riftar.domain.searchhistory.usecase

import androidx.paging.PagingData
import com.riftar.domain.searchhistory.model.StockHistory
import com.riftar.domain.searchhistory.repository.SearchStockRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchStockHistoryByQueryTest {

    @Mock
    private lateinit var repository: SearchStockRepository

    private lateinit var useCase: SearchStockHistoryByQuery

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        useCase = SearchStockHistoryByQuery(repository)
    }

    @Test
    fun `searchStockHistoryByQuery emits success when repository succeeds`(): Unit = runBlocking {
        val query = "AAPL"
        val list = listOf(StockHistory("AAPL", "Apple Inc.", 1723798058816, 122.13, 14.1))
        val pagingData = PagingData.from(list)
        `when`(repository.getListUserByQuery(query)).thenReturn(
            flowOf(
                pagingData
            )
        )

        val result = useCase(query).toList()

        assertEquals(pagingData, result.first())
        verify(repository).getListUserByQuery(query)
    }
}