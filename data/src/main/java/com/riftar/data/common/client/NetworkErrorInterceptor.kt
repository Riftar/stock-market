package com.riftar.data.common.client

import android.accounts.NetworkErrorException
import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkErrorInterceptor(context: Context) : Interceptor {
    private val mContext: Context = context
    private val isConnected: Boolean
        get() {
            val connectivityManager =
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            return capabilities != null
        }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected) {
            throw NoInternetException()
            // Throwing our custom exception 'NoInternetException'
        }
        val builder: Request.Builder = chain.request().newBuilder()
        return try {
            chain.proceed(builder.build())
        } catch (e: SocketTimeoutException) {
            throw NoInternetException()
        } catch (e: Exception) {
            if (e.isNetworkError()) {
                throw NoInternetException()
            }
            chain.proceed(builder.build())
        }
    }

    private fun Exception.isNetworkError(): Boolean {
        return this is NetworkErrorException ||
                this is UnknownHostException ||
                this is ConnectException
    }
}

class NoInternetException(private val errorMessage: String = "No internet connection!") : IOException() {
    // You can send any message whatever you want from here.
    override val message: String
        get() = errorMessage
}