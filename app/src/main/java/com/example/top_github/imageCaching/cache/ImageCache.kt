package com.example.top_github.imageCaching.cache

import android.graphics.Bitmap

/**
 * An interface which provides all basic methods to put and get bitmaps from cache
 * Disk cache and Memory cache will implement this interface
 */
interface ImageCache {
    fun put(url: String, bitmap: Bitmap)
    fun get(url: String): Bitmap?
    fun clear()
}