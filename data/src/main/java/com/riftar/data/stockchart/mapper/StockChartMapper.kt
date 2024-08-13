package com.riftar.data.stockchart.mapper

import com.riftar.common.helper.orFalse
import com.riftar.common.helper.orZero
import com.riftar.data.stockchart.response.CurrentTradingPeriodResponse
import com.riftar.data.stockchart.response.IndicatorsResponse
import com.riftar.data.stockchart.response.MetaResponse
import com.riftar.data.stockchart.response.PostResponse
import com.riftar.data.stockchart.response.PreResponse
import com.riftar.data.stockchart.response.QuoteResponse
import com.riftar.data.stockchart.response.RegularResponse
import com.riftar.data.stockchart.response.ResultResponse
import com.riftar.data.stockchart.response.TradingPeriodResponse
import com.riftar.domain.stockchart.model.CurrentTradingPeriod
import com.riftar.domain.stockchart.model.Indicators
import com.riftar.domain.stockchart.model.Meta
import com.riftar.domain.stockchart.model.Post
import com.riftar.domain.stockchart.model.Pre
import com.riftar.domain.stockchart.model.Quote
import com.riftar.domain.stockchart.model.Regular
import com.riftar.domain.stockchart.model.ChartResult
import com.riftar.domain.stockchart.model.TradingPeriod


fun ResultResponse?.toDomainModel() = ChartResult(
    meta = this?.meta.toDomainModel(),
    timestamp = this?.timestamp.orEmpty().map { it.orZero() },
    indicators = this?.indicators.toDomainModel()
)

fun MetaResponse?.toDomainModel() = Meta(
    currency = this?.currency.orEmpty(),
    symbol = this?.symbol.orEmpty(),
    exchangeName = this?.exchangeName.orEmpty(),
    fullExchangeName = this?.fullExchangeName.orEmpty(),
    instrumentType = this?.instrumentType.orEmpty(),
    firstTradeDate = this?.firstTradeDate.orZero(),
    regularMarketTime = this?.regularMarketTime.orZero(),
    hasPrePostMarketData = this?.hasPrePostMarketData.orFalse(),
    gmtoffset = this?.gmtoffset.orZero(),
    timezone = this?.timezone.orEmpty(),
    exchangeTimezoneName = this?.exchangeTimezoneName.orEmpty(),
    regularMarketPrice = this?.regularMarketPrice.orZero(),
    fiftyTwoWeekHigh = this?.fiftyTwoWeekHigh.orZero(),
    fiftyTwoWeekLow = this?.fiftyTwoWeekLow.orZero(),
    regularMarketDayHigh = this?.regularMarketDayHigh.orZero(),
    regularMarketDayLow = this?.regularMarketDayLow.orZero(),
    regularMarketVolume = this?.regularMarketVolume.orZero(),
    longName = this?.longName.orEmpty(),
    shortName = this?.shortName.orEmpty(),
    chartPreviousClose = this?.chartPreviousClose.orZero(),
    previousClose = this?.previousClose.orZero(),
    scale = this?.scale.orZero(),
    priceHint = this?.priceHint.orZero(),
    currentTradingPeriod = this?.currentTradingPeriod.toDomainModel(),
    tradingPeriods = this?.tradingPeriods.orEmpty()
        .map { it.orEmpty().map { data -> data.toDomainModel() } },
    dataGranularity = this?.dataGranularity.orEmpty(),
    range = this?.range.orEmpty(),
    validRanges = this?.validRanges.orEmpty().map { it.orEmpty() }
)

fun CurrentTradingPeriodResponse?.toDomainModel() = CurrentTradingPeriod(
    pre = this?.pre.toDomainModel(),
    regular = this?.regular.toDomainModel(),
    post = this?.post.toDomainModel()
)

fun PreResponse?.toDomainModel() = Pre(
    timezone = this?.timezone.orEmpty(),
    end = this?.end.orZero(),
    start = this?.start.orZero(),
    gmtoffset = this?.gmtoffset.orZero()
)

fun RegularResponse?.toDomainModel() = Regular(
    timezone = this?.timezone.orEmpty(),
    end = this?.end.orZero(),
    start = this?.start.orZero(),
    gmtoffset = this?.gmtoffset.orZero()
)

fun PostResponse?.toDomainModel() = Post(
    timezone = this?.timezone.orEmpty(),
    end = this?.end.orZero(),
    start = this?.start.orZero(),
    gmtoffset = this?.gmtoffset.orZero()
)

fun TradingPeriodResponse?.toDomainModel() = TradingPeriod(
    timezone = this?.timezone.orEmpty(),
    end = this?.end.orZero(),
    start = this?.start.orZero(),
    gmtoffset = this?.gmtoffset.orZero()
)

fun IndicatorsResponse?.toDomainModel() = Indicators(
    quote = this?.quote.orEmpty().map { it.toDomainModel() }
)

fun QuoteResponse?.toDomainModel() = Quote(
    close = this?.close.orEmpty().map { it.orZero() },
    high = this?.high.orEmpty().map { it.orZero() },
    low = this?.low.orEmpty().map { it.orZero() },
    `open` = this?.`open`.orEmpty().map { it.orZero() },
    volume = this?.volume.orEmpty().map { it.orZero() }
)
