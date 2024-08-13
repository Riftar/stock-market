package com.riftar.stockmarket

import android.app.Application
import com.riftar.stockmarket.di.KoinInitializer

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        KoinInitializer.init(this)
    }
}