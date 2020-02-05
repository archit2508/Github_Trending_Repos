package com.example.top_github.imageCaching.cache

/**
 * Utils class used for image caching package
 */
class Config {
    companion object {
        val maxMemory = Runtime.getRuntime().maxMemory() /1024
        val defaultCacheSize = (maxMemory /4).toInt()
    }
}