package com.riftar.domain.stockchart.model

data class ChartResult(
    val meta: Meta,
    val timestamp: List<Long>,
    val indicators: Indicators
)