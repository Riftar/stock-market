package com.riftar.data.stockchart.repository

import com.riftar.data.stockchart.api.StockChartAPI
import com.riftar.data.stockchart.response.ChartResponse
import com.riftar.data.stockchart.response.ChartResponseWrapper
import com.riftar.data.stockchart.response.CurrentTradingPeriodResponse
import com.riftar.data.stockchart.response.ErrorResponse
import com.riftar.data.stockchart.response.IndicatorsResponse
import com.riftar.data.stockchart.response.MetaResponse
import com.riftar.data.stockchart.response.QuoteResponse
import com.riftar.data.stockchart.response.ResultResponse
import com.riftar.data.stockchart.response.TradingPeriodResponse
import com.riftar.domain.stockchart.model.ChartResult
import com.riftar.domain.stockchart.model.CurrentTradingPeriod
import com.riftar.domain.stockchart.model.Indicators
import com.riftar.domain.stockchart.model.Meta
import com.riftar.domain.stockchart.model.Quote
import com.riftar.domain.stockchart.model.TradingPeriod
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class StockChartRepositoryImplTest {

    @Mock
    private lateinit var api: StockChartAPI

    private lateinit var repository: StockChartRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = StockChartRepositoryImpl(api)
    }

    @Test
    fun `getStockChart should emit success when API call is successful`() = runTest {
        val mockResponse = ChartResponseWrapper(
            chart = ChartResponse(
                result = listOf(chartResultResponse)
            )
        )
        val mockChartResult = chartResult

        `when`(api.getStockChart("AAPL",  "2m", "1d")).thenReturn(
            Response.success(200, mockResponse)
        )

        val result = repository.getStockChart("AAPL",  "2m", "1d").first()

        assertTrue(result.isSuccess)
        assertEquals(mockChartResult, result.getOrNull())
        verify(api).getStockChart("AAPL",  "2m", "1d")
    }

    @Test
    fun `getStockChart should emit failure when API return error`() = runTest {
        val mockResponse = ChartResponseWrapper(
            chart = ChartResponse(
                error = ErrorResponse(
                    code = "123",
                    description = "Error From API Response"
                )
            )
        )

        `when`(api.getStockChart("AAPL",  "2m", "1d")).thenReturn(
            Response.success(200, mockResponse)
        )

        val result = repository.getStockChart("AAPL",  "2m", "1d").first()

        assertTrue(result.isFailure)
        assertEquals("Error From API Response", result.exceptionOrNull()?.message)
        verify(api).getStockChart("AAPL",  "2m", "1d")
    }


    @Test
    fun `getStockChart should emit failure when API return 404`() = runTest {
        `when`(api.getStockChart("AAPL",  "2m", "1d")).thenReturn(
            Response.error(404, "".toResponseBody(null))
        )

        val result = repository.getStockChart("AAPL",  "2m", "1d").first()

        assertTrue(result.isFailure)
        assertEquals("No data found, symbol may be delisted", result.exceptionOrNull()?.message)
        verify(api).getStockChart("AAPL",  "2m", "1d")
    }

    @Test
    fun `getStockChart should emit failure when API call is unsuccessful`() = runTest {

        `when`(api.getStockChart("AAPL",  "2m", "1d")).thenReturn(
            Response.error(500, "".toResponseBody(null))
        )

        val result = repository.getStockChart("AAPL",  "2m", "1d").first()
        assertTrue(result.isFailure)
        assertEquals("Unknown error", result.exceptionOrNull()?.message)
        verify(api).getStockChart("AAPL",  "2m", "1d")
    }

    @Test
    fun `getStockChart should emit failure when exception is thrown`() = runTest {
        `when`(api.getStockChart("AAPL",  "2m", "1d")).thenThrow(RuntimeException("Error Occurred"))

        val result = repository.getStockChart("AAPL",  "2m", "1d").last()
        assertTrue(result.isFailure)
        assertEquals("Error Occurred", result.exceptionOrNull()?.message)
        verify(api).getStockChart("AAPL",  "2m", "1d")
    }

    @Test
    fun `getStockChart should retry 3 times when IOException is thrown`() = runTest {
        `when`(api.getStockChart("AAPL",  "2m", "1d")).thenThrow(IOException("Error Occurred"))

        val result = repository.getStockChart("AAPL",  "2m", "1d").last()
        assertTrue(result.isFailure)
        assertEquals("Error Occurred", result.exceptionOrNull()?.message)
        verify(api, times(4)).getStockChart("AAPL",  "2m", "1d")
    }

    @Test
    fun `getStockChart should not retry when exception besides IOException is thrown`() = runTest {
        `when`(api.getStockChart("AAPL",  "2m", "1d")).thenThrow(RuntimeException("Error Occurred"))

        val result = repository.getStockChart("AAPL",  "2m", "1d").last()
        assertTrue(result.isFailure)
        assertEquals("Error Occurred", result.exceptionOrNull()?.message)
        verify(api, times(1)).getStockChart("AAPL",  "2m", "1d")
    }

    private val chartResultResponse = ResultResponse(
        meta = MetaResponse(
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
            currentTradingPeriod = CurrentTradingPeriodResponse(
                pre = TradingPeriodResponse(
                    timezone = "EDT",
                    end = 1723815000,
                    start = 1723795200,
                    gmtoffset = -14400
                ), regular = TradingPeriodResponse(
                    timezone = "EDT",
                    end = 1723815000,
                    start = 1723795200,
                    gmtoffset = -14400
                ), post = TradingPeriodResponse(
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
        indicators = IndicatorsResponse(
            quote = listOf(
                QuoteResponse(
                    close = listOf(224.72),
                    high = listOf(224.76),
                    low = listOf(224.34),
                    open = listOf(224.72),
                    volume = listOf(346879)
                )
            )
        )
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