package com.riftar.domain.stockchart.model

data class CurrentTradingPeriod(
    val pre: Pre,
    val regular: Regular,
    val post: Post
)