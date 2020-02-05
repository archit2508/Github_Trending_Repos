package com.example.top_github.imageCaching.cache

import android.graphics.Bitmap
import android.util.LruCache

/**
 * Class implementing LRU cache cache by using a map to store bitmap as values
 * Uses url of image as key in map
 */
class MemoryCache (newMaxSize: Int) :
    ImageCache {
    private val cache : LruCache<String, Bitmap>
    init {
        val cacheSize : Int = if (newMaxSize > Config.maxMemory) {
            Config.defaultCacheSize
        } else {
            newMaxSize
        }
        cache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String, value: Bitmap): Int {
                return (value.rowBytes)*(value.height)/1024
            }
        }
    }

    override fun put(url: String, bitmap: Bitmap) {
        cache.put(url,bitmap)
    }

    override fun get(url: String): Bitmap? {
        return  cache.get(url)
    }

    override fun clear() {
        cache.evictAll()
    }
}