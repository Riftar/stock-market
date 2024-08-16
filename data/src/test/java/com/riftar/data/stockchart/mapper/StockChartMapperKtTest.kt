package com.riftar.data.stockchart.mapper

import com.riftar.data.stockchart.response.CurrentTradingPeriodResponse
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
import kotlinx.coroutines.test.runTest
import org.junit.Test

class StockChartMapperKtTest {

    @Test
    fun testToDomainModel() = runTest {
        assert(chartResultResponse.toDomainModel() == chartResult)
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