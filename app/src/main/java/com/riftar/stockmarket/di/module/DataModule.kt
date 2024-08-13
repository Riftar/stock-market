package com.riftar.stockmarket.di.module

import com.riftar.data.common.client.createOkHttp
import com.riftar.data.common.client.createRetrofit
import com.riftar.data.common.client.provideNetworkErrorInterceptor
import org.koin.dsl.module

val dataModule = module {
    single { provideNetworkErrorInterceptor(get()) }
    single {
        createOkHttp(get())
    }
    single {
        createRetrofit(get())
    }
}