package com.riftar.data.common.client

import android.content.Context
import com.riftar.data.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val TIME_OUT_SECONDS = 30L
fun createOkHttp(
    networkErrorInterceptor: NetworkErrorInterceptor
): OkHttpClient {
    val okHttpBuilder = OkHttpClient.Builder()
    okHttpBuilder.addInterceptor(networkErrorInterceptor)
    if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpBuilder.addInterceptor(loggingInterceptor)
    }
    okHttpBuilder.connectTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
    okHttpBuilder.readTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
    okHttpBuilder.writeTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
    return okHttpBuilder.build()
}

fun createRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}

fun provideNetworkErrorInterceptor(context: Context) = NetworkErrorInterceptor(context)