package com.example.top_github.data.interceptor

import android.content.Context
import com.example.top_github.data.interceptor.ConnectivityStatus.Companion.isConnected
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * WIll retain and return cached api response only when online
 * will retain online cache for 10 seconds
 */
class OnlineCacheInterceptor(private val context: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        val cacheControl = originalResponse.header("Cache-Control")
        return if (cacheControl == null || cacheControl!!.contains("no-store") || cacheControl!!.contains("no-cache") ||
            cacheControl!!.contains("must-revalidate") || cacheControl!!.contains("max-age=0")
        ) {
            originalResponse.newBuilder()
                .header("Cache-Control", "public, max-age=" + 10) //keep cache for 10 seconds when online
                .build()
        } else {
            originalResponse
        }
    }
}