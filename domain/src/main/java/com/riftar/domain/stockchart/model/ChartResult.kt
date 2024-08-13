package com.riftar.domain.stockchart.model

data class ChartResult(
    val meta: Meta,
    val timestamp: List<Int>,
    val indicators: Indicators
)