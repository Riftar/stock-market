package com.riftar.domain.stockchart.usecase

import com.riftar.domain.stockchart.model.ChartResult
import com.riftar.domain.stockchart.model.CurrentTradingPeriod
import com.riftar.domain.stockchart.model.Indicators
import com.riftar.domain.stockchart.model.Meta
import com.riftar.domain.stockchart.model.Quote
import com.riftar.domain.stockchart.model.TradingPeriod
import com.riftar.domain.stockchart.repository.StockChartRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.never

@RunWith(MockitoJUnitRunner::class)
class GetStockChartUseCaseTest {

    @Mock
    private lateinit var repository: StockChartRepository

    private lateinit var useCase: GetStockChartUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        useCase = GetStockChartUseCase(repository)
    }

    @Test
    fun `calculateInterval returns the correct value for 1d`(): Unit = runBlocking {
        val period = "1d"
        val expectedInterval = "2m"
        val chartResult = chartResultDummy
        `when`(
            repository.getStockChart(
                "AAPL",
                expectedInterval,
                period
            )
        ).thenReturn(flowOf(Result.success(chartResult)))

        val result = useCase(stockCode = "AAPL", period = period).first()

        assertEquals(Result.success(chartResult), result)
        // Verify that the repository is called with the correct interval
        verify(repository).getStockChart(eq("AAPL"), eq(expectedInterval), eq(period))
    }

    @Test
    fun `calculateInterval returns the correct value for 5d`(): Unit = runBlocking {
        val period = "5d"
        val expectedInterval = "15m"
        val chartResult = chartResultDummy
        `when`(
             repository.getStockChart(
                "AAPL",
                expectedInterval,
                period
            )
        ).thenReturn(flowOf(Result.success(chartResult)))

        val result = useCase(stockCode = "AAPL", period = period).first()

        assertEquals(Result.success(chartResult), result)
        // Verify that the repository is called with the correct interval
        verify(repository).getStockChart(eq("AAPL"), eq(expectedInterval), eq(period))
    }

    @Test
    fun `calculateInterval returns the correct value for 1month`(): Unit = runBlocking {
        val period = "1mo"
        val expectedInterval = "1h"
        val chartResult = chartResultDummy
        `when`(
             repository.getStockChart(
                "AAPL",
                expectedInterval,
                period
            )
        ).thenReturn(flowOf(Result.success(chartResult)))

        val result = useCase(stockCode = "AAPL", period = period).first()

        assertEquals(Result.success(chartResult), result)
        // Verify that the repository is called with the correct interval
        verify(repository).getStockChart(eq("AAPL"), eq(expectedInterval), eq(period))
    }

    @Test
    fun `calculateInterval returns the correct value for 6mo`(): Unit = runBlocking {
        val period = "6mo"
        val expectedInterval = "1d"
        val chartResult = chartResultDummy
        `when`(
             repository.getStockChart(
                "AAPL",
                expectedInterval,
                period
            )
        ).thenReturn(flowOf(Result.success(chartResult)))

        val result = useCase(stockCode = "AAPL", period = period).first()

        assertEquals(Result.success(chartResult), result)
        // Verify that the repository is called with the correct interval
        verify(repository).getStockChart(eq("AAPL"), eq(expectedInterval), eq(period))
    }

    @Test
    fun `calculateInterval returns the correct value for ytd`(): Unit = runBlocking {
        val period = "ytd"
        val expectedInterval = "5d"
        val chartResult = chartResultDummy
        `when`(
             repository.getStockChart(
                "AAPL",
                expectedInterval,
                period
            )
        ).thenReturn(flowOf(Result.success(chartResult)))

        val result = useCase(stockCode = "AAPL", period = period).first()

        assertEquals(Result.success(chartResult), result)
        // Verify that the repository is called with the correct interval
        verify(repository).getStockChart(eq("AAPL"), eq(expectedInterval), eq(period))
    }


    @Test
    fun `calculateInterval throws exception for invalid period`(): Unit = runBlocking {
        val period = "7d"
        val result = useCase(stockCode = "AAPL", period = period).first()
        assert(result.isFailure)
        assertEquals("Invalid period: $period", result.exceptionOrNull()?.message)
        verify(repository, never()).getStockChart(any(), any(), any())
    }

    private val chartResultDummy = ChartResult(
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