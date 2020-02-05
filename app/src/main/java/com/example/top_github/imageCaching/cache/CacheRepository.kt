package com.example.top_github.imageCaching.cache

import android.content.Context
import android.graphics.Bitmap

/**
 * Main Repository which puts and gets bitmap data to/from both Memory and Disk cache
 */
class CacheRepository (context: Context , newMaxSize: Int ): ImageCache{

    private val diskCache = DiskCache.getInstance(context)
    private val memoryCache = MemoryCache(newMaxSize)

    override fun put(url: String, bitmap: Bitmap) {
        memoryCache.put(url,bitmap)
        diskCache.put(url,bitmap)
    }

    override fun get(url: String): Bitmap? {
        return memoryCache.get(url)?:diskCache.get(url)
    }

    override fun clear() {
        memoryCache.clear()
        diskCache.clear()
    }
}