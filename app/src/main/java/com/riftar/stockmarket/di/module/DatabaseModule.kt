package com.riftar.stockmarket.di.module

import android.content.Context
import androidx.room.Room
import com.riftar.data.common.database.AppDatabase
import com.riftar.data.searchhistory.room.dao.SearchHistoryDao
import org.koin.dsl.module

val databaseModule = module {
    single { provideCustomerListDatabase(get()) }
    single { provideSearchHistoryDao(get()) }
}

fun provideCustomerListDatabase(context: Context): AppDatabase =
    Room.databaseBuilder(context, AppDatabase::class.java, "stock_market_db")
        .fallbackToDestructiveMigration()
        .build()

fun provideSearchHistoryDao(db: AppDatabase): SearchHistoryDao = db.getSearchHistoryDao()