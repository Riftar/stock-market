package com.riftar.stockmarket.di.module

import com.riftar.data.common.client.createOkHttp
import com.riftar.data.common.client.createRetrofit
import com.riftar.data.common.client.provideNetworkErrorInterceptor
import com.riftar.data.stockchart.api.StockChartAPI
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {
    single { provideNetworkErrorInterceptor(get()) }
    single {
        createOkHttp(get())
    }
    single {
        createRetrofit(get())
    }

    single { provideStockChartAPI(get()) }
}

fun provideStockChartAPI(retrofit: Retrofit): StockChartAPI =
    retrofit.create(StockChartAPI::class.java)