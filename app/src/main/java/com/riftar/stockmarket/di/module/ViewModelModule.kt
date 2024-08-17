package com.riftar.stockmarket.di.module

import com.riftar.searchstock.SearchStockViewModel
import com.riftar.stockchart.StockChartViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {

    viewModel { StockChartViewModel(get(), get()) }
    viewModel { SearchStockViewModel(get()) }
}