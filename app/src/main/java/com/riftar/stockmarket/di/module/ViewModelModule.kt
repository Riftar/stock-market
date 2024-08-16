package com.riftar.stockmarket.di.module

import com.riftar.stockchart.StockChartViewModel
import com.riftar.stockchart.search.SearchStockViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {

    viewModel { StockChartViewModel(get(), get()) }
    viewModel { SearchStockViewModel(get()) }
}