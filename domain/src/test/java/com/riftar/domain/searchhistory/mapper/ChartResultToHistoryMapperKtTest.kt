package com.riftar.domain.searchhistory.mapper

import com.riftar.domain.searchhistory.model.StockHistory
import com.riftar.domain.stockchart.model.ChartResult
import com.riftar.domain.stockchart.model.CurrentTradingPeriod
import com.riftar.domain.stockchart.model.Indicators
import com.riftar.domain.stockchart.model.Meta
import com.riftar.domain.stockchart.model.Quote
import com.riftar.domain.stockchart.model.TradingPeriod
import org.junit.Test

class ChartResultToHistoryMapperKtTest {

    @Test
    fun testCalculateGainOrLoss() {
        val regularMarketPrice = chartResult.meta.regularMarketPrice
        val previousClose = chartResult.meta.previousClose
        val diff = regularMarketPrice - previousClose
        val gainOrLoss = chartResult.calculateGainOrLoss()
        assert(gainOrLoss == diff)
    }

    @Test
    fun testCalculatePercentageChange() {
        val regularMarketPrice = chartResult.meta.regularMarketPrice
        val previousClose = chartResult.meta.previousClose
        val percentageChange = (regularMarketPrice - previousClose) / previousClose * 100
        assert(chartResult.calculatePercentageChange() == percentageChange)
    }

    @Test
    fun testToStockHistory() {
        val stockHistory = StockHistory(
            symbol = chartResult.meta.symbol,
            shortName = chartResult.meta.shortName,
            searchTimeMillis = 1723798058816,
            close = chartResult.indicators.quote.getOrNull(0)?.close?.lastOrNull() ?: 0.0,
            percentageChange = chartResult.calculatePercentageChange()
        )
        assert(stockHistory == chartResult.toStockHistory(1723798058816))
    }

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