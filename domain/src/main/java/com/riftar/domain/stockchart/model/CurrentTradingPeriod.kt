package com.riftar.domain.stockchart.model

data class CurrentTradingPeriod(
    val pre: TradingPeriod,
    val regular: TradingPeriod,
    val post: TradingPeriod
)