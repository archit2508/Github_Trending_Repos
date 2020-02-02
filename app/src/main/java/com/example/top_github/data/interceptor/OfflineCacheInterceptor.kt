package com.example.top_github.data.interceptor

import android.content.Context
import com.example.top_github.data.interceptor.ConnectivityStatus.Companion.isConnected
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class OfflineCacheInterceptor(private val context: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!isConnected(context)!!) {
            val maxStale = 60 * 60 * 24 * 7
            request = request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .build()
        }
        return chain.proceed(request)
    }
}