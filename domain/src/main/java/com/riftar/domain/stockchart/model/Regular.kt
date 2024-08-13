package com.riftar.domain.stockchart.model

data class Regular(
    val timezone: String,
    val end: Int,
    val start: Int,
    val gmtoffset: Int
)