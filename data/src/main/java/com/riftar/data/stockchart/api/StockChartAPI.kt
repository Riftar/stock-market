package com.riftar.data.stockchart.api

import com.riftar.data.stockchart.response.ChartResponseWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StockChartAPI {
    @GET("chart/{stock_code}")
    suspend fun getStockChart(
        @Path("stock_code") stockCode: String,
    ): Response<ChartResponseWrapper>
}