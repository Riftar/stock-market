package com.riftar.data.stockchart.api

import com.riftar.data.stockchart.response.ChartResponseWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StockChartAPI {
    @GET("chart/{stock_code}?region=US&lang=en-US&includePrePost=false&interval=15m&range=5d")
    suspend fun getStockChart(
        @Path("stock_code") stockCode: String,
    ): Response<ChartResponseWrapper>
}