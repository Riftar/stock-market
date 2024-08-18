package com.riftar.data.stockchart.api

import com.riftar.data.stockchart.response.ChartResponseWrapper
import okio.IOException
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StockChartAPI {
    @Throws(IOException::class)
    @GET("v8/finance/chart/{stock_code}?region=US&lang=en-US&includePrePost=false&useYfid=true")
    suspend fun getStockChart(
        @Path("stock_code") stockCode: String,
        @Query("interval") interval: String,
        @Query("range") periodRange: String,
    ): Response<ChartResponseWrapper>
}