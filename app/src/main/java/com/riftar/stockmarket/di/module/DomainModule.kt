package com.riftar.stockmarket.di.module

import com.riftar.data.searchhistory.repository.SearchStockRepositoryImpl
import com.riftar.data.stockchart.repository.StockChartRepositoryImpl
import com.riftar.domain.searchhistory.repository.SearchStockRepository
import com.riftar.domain.searchhistory.usecase.SaveCurrentSearchToHistoryUseCase
import com.riftar.domain.searchhistory.usecase.SearchStockHistoryByQuery
import com.riftar.domain.stockchart.repository.StockChartRepository
import com.riftar.domain.stockchart.usecase.GetStockChartUseCase
import org.koin.dsl.module

val domainModule = module {
    single<StockChartRepository> { StockChartRepositoryImpl(get()) }
    single<SearchStockRepository> { SearchStockRepositoryImpl(get()) }
    factory { GetStockChartUseCase(get()) }
    factory { SearchStockHistoryByQuery(get()) }
    factory { SaveCurrentSearchToHistoryUseCase(get()) }
}