package com.riftar.stockmarket.di.module

import com.riftar.data.stockchart.repository.StockChartRepositoryImpl
import com.riftar.domain.stockchart.repository.StockChartRepository
import com.riftar.domain.stockchart.usecase.GetStockChartUseCase
import org.koin.dsl.module

val domainModule = module {
    single<StockChartRepository> { StockChartRepositoryImpl(get()) }
    single { GetStockChartUseCase(get()) }
}