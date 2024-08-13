package com.riftar.stockmarket.di

import android.content.Context
import com.riftar.stockmarket.di.module.dataModule
import com.riftar.stockmarket.di.module.databaseModule
import com.riftar.stockmarket.di.module.domainModule
import com.riftar.stockmarket.di.module.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


object KoinInitializer {

    fun init(context: Context) {
        startKoin {
            androidContext(context)

            modules(
                databaseModule,
                dataModule,
                domainModule,
                viewModule,
            )
        }
    }
}