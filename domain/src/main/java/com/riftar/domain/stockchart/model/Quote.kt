package com.riftar.domain.stockchart.model

data class Quote(
    val low: List<Double>,
    val `open`: List<Double>,
    val high: List<Double>,
    val volume: List<Int>,
    val close: List<Double>
)