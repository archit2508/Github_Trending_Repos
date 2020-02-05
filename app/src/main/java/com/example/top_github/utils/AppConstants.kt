package com.example.top_github.utils

/**
 * Global class which stored const values
 */
class AppConstants {
    companion object{
        const val CACHE_SIZE: Int = 10 * 1024 * 1024
        val INTENT_DATA: String? = "INTENT_DATA"
        const val BASE_URL = "https://github-trending-api.now.sh/"
    }
}