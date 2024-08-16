package com.riftar.domain.searchhistory.usecase

import com.riftar.domain.searchhistory.model.StockHistory
import com.riftar.domain.searchhistory.repository.SearchStockRepository
import com.riftar.domain.stockchart.model.ChartResult
import com.riftar.domain.stockchart.model.CurrentTradingPeriod
import com.riftar.domain.stockchart.model.Indicators
import com.riftar.domain.stockchart.model.Meta
import com.riftar.domain.stockchart.model.Quote
import com.riftar.domain.stockchart.model.TradingPeriod
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
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
import org.mockito.kotlin.any

@RunWith(MockitoJUnitRunner::class)
class SaveCurrentSearchToHistoryUseCaseTest {

    @Mock
    private lateinit var repository: SearchStockRepository

    private lateinit var useCase: SaveCurrentSearchToHistoryUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        useCase = SaveCurrentSearchToHistoryUseCase(repository)
    }

    @Test
    fun `saveCurrentSearchToHistory emits success when repository succeeds`(): Unit = runBlocking {
        val chartResult = chartResult
        val searchTimeMillis = 1234567890L
        `when`(repository.saveCurrentSearchToHistory(any())).thenReturn(
            flowOf(
                Result.success(
                    Unit
                )
            )
        )

        val result = useCase(chartResult, searchTimeMillis).last()

        assertTrue(result.isSuccess)
        verify(repository).saveCurrentSearchToHistory(any())
    }

    @Test
    fun `saveCurrentSearchToHistory emits failure when repository fails`(): Unit = runBlocking {
        val chartResult = chartResult
        val searchTimeMillis = 1234567890L
        val expectedException = Exception("Failed to save search history")
        `when`(repository.saveCurrentSearchToHistory(any())).thenReturn(
            flowOf(
                Result.failure(
                    expectedException
                )
            )
        )

        val result = useCase(chartResult, searchTimeMillis).toList()

        assertTrue(result.first().isFailure)
        assertEquals(expectedException, result.first().exceptionOrNull())
        verify(repository).saveCurrentSearchToHistory(any())
    }

    @Test
    fun `saveCurrentSearchToHistory emits failure when error are thrown`(): Unit = runBlocking {
        val chartResult = chartResult
        val searchTimeMillis = 1234567890L
        val expectedException = RuntimeException("Error Occurred")
        `when`(repository.saveCurrentSearchToHistory(any())).thenThrow(expectedException)

        val result = useCase(chartResult, searchTimeMillis).toList()

        assertTrue(result.first().isFailure)
        assertEquals(expectedException, result.first().exceptionOrNull())
        verify(repository).saveCurrentSearchToHistory(any())
    }

    private val stockHistory = StockHistory(
        symbol = "AAPL",
        shortName = "Apple Inc.",
        close = 221.72,
        percentageChange = 14.1,
        searchTimeMillis = 1234567890L
    )
    private val chartResult = ChartResult(
        meta = Meta(
            regularMarketPrice = 224.72,
            previousClose = 221.72,
            currency = "USD",
            symbol = "AAPL",
            exchangeName = "NMS",
            fullExchangeName = "NasdaqGS",
            instrumentType = "ante",
            firstTradeDate = 345479400,
            regularMarketTime = 1723752001,
            hasPrePostMarketData = false,
            gmtoffset = -14400,
            timezone = "EDT",
            exchangeTimezoneName = "America/New_York",
            fiftyTwoWeekHigh = 224.72,
            fiftyTwoWeekLow = 224.34,
            regularMarketDayHigh = 224.76,
            regularMarketDayLow = 224.72,
            regularMarketVolume = 45089257,
            longName = "Apple Inc.",
            shortName = "Apple Inc.",
            chartPreviousClose = 221.72,
            scale = 3,
            priceHint = 2,
            currentTradingPeriod = CurrentTradingPeriod(
                pre = TradingPeriod(
                    timezone = "EDT",
                    end = 1723815000,
                    start = 1723795200,
                    gmtoffset = -14400
                ), regular = TradingPeriod(
                    timezone = "EDT",
                    end = 1723815000,
                    start = 1723795200,
                    gmtoffset = -14400
                ), post = TradingPeriod(
                    timezone = "EDT",
                    end = 1723815000,
                    start = 1723795200,
                    gmtoffset = -14400
                )
            ),
            tradingPeriods = listOf(),
            dataGranularity = "1m",
            range = "1d",
            validRanges = listOf(),
        ),
        timestamp = listOf(),
        indicators = Indicators(
            quote = listOf(
                Quote(
                    close = listOf(224.72),
                    high = listOf(224.76),
                    low = listOf(224.34),
                    open = listOf(224.72),
                    volume = listOf(346879)
                )
            )
        )
    )
}