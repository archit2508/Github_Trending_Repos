package com.example.top_github.data.interceptor

import android.content.Context
import android.content.ContextWrapper
import android.net.ConnectivityManager

class ConnectivityStatus(base: Context?) : ContextWrapper(base) {
    companion object {
        @JvmStatic
        fun isConnected(context: Context): Boolean {
            val manager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val connection = manager.activeNetworkInfo
            return connection != null && connection.isConnectedOrConnecting
        }
    }
}