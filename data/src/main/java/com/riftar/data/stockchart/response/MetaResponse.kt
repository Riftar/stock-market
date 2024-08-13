package com.riftar.data.stockchart.response


import com.google.gson.annotations.SerializedName

data class MetaResponse(
    @SerializedName("chartPreviousClose")
    val chartPreviousClose: Double? = null,
    @SerializedName("currency")
    val currency: String? = null,
    @SerializedName("currentTradingPeriod")
    val currentTradingPeriod: CurrentTradingPeriodResponse? = null,
    @SerializedName("dataGranularity")
    val dataGranularity: String? = null,
    @SerializedName("exchangeName")
    val exchangeName: String? = null,
    @SerializedName("exchangeTimezoneName")
    val exchangeTimezoneName: String? = null,
    @SerializedName("fiftyTwoWeekHigh")
    val fiftyTwoWeekHigh: Double? = null,
    @SerializedName("fiftyTwoWeekLow")
    val fiftyTwoWeekLow: Double? = null,
    @SerializedName("firstTradeDate")
    val firstTradeDate: Int? = null,
    @SerializedName("fullExchangeName")
    val fullExchangeName: String? = null,
    @SerializedName("gmtoffset")
    val gmtoffset: Int? = null,
    @SerializedName("hasPrePostMarketData")
    val hasPrePostMarketData: Boolean? = null,
    @SerializedName("instrumentType")
    val instrumentType: String? = null,
    @SerializedName("longName")
    val longName: String? = null,
    @SerializedName("previousClose")
    val previousClose: Double? = null,
    @SerializedName("priceHint")
    val priceHint: Int? = null,
    @SerializedName("range")
    val range: String? = null,
    @SerializedName("regularMarketDayHigh")
    val regularMarketDayHigh: Double? = null,
    @SerializedName("regularMarketDayLow")
    val regularMarketDayLow: Double? = null,
    @SerializedName("regularMarketPrice")
    val regularMarketPrice: Double? = null,
    @SerializedName("regularMarketTime")
    val regularMarketTime: Int? = null,
    @SerializedName("regularMarketVolume")
    val regularMarketVolume: Double? = null,
    @SerializedName("scale")
    val scale: Int? = null,
    @SerializedName("shortName")
    val shortName: String? = null,
    @SerializedName("symbol")
    val symbol: String? = null,
    @SerializedName("timezone")
    val timezone: String? = null,
    @SerializedName("tradingPeriods")
    val tradingPeriods: List<List<TradingPeriodResponse?>?>? = null,
    @SerializedName("validRanges")
    val validRanges: List<String?>? = null
)