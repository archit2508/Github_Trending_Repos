package com.example.top_github.imageCaching.core

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.example.top_github.imageCaching.async.DownloadImageTask
import com.example.top_github.imageCaching.async.DownloadTask
import com.example.top_github.imageCaching.cache.CacheRepository
import com.example.top_github.imageCaching.cache.Config
import java.util.concurrent.Executors
import java.util.concurrent.Future

/**
 * This manager will be exposed to outer world to load and cache images downloaded from web
 */
class ImageManager private constructor(context: Context, cacheSize: Int) {
    private val cache = CacheRepository(context, cacheSize)
    private val executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
    private val mRunningDownloadList:HashMap<String,Future<Bitmap?>> = hashMapOf()


    fun displayImage(url: String, imageView: ImageView, placeholder: Int) {
        var bitmap = cache.get(url)
        bitmap?.let {
            imageView.setImageBitmap(it)
            return
        } ?: run {
                imageView.tag = url
                imageView.setImageResource(placeholder)
                addDownloadImageTask( url, DownloadImageTask(url , imageView , cache)) }

    }


    private fun addDownloadImageTask(url: String, downloadTask: DownloadTask<Bitmap?>) {
        mRunningDownloadList[url] = executorService.submit(downloadTask)
    }


    fun clearcache() {
        cache.clear()
    }

    fun cancelTask(url: String){
        synchronized(this){
            mRunningDownloadList.forEach {
                if (it.key == url &&  !it.value.isDone)
                    it.value.cancel(true)
            }
        }
    }

    fun  cancelAll() {
        synchronized (this) {
            mRunningDownloadList.forEach{
                if ( !it.value.isDone)
                    it.value.cancel(true)
            }
            mRunningDownloadList.clear()
        }
    }


    companion object {
        private val INSTANCE: ImageManager? = null
        @Synchronized
        fun getInstance(context: Context, cacheSize: Int = Config.defaultCacheSize): ImageManager {
            return INSTANCE?.let { return INSTANCE }
                ?: run {
                    return ImageManager(context, cacheSize)
                }
        }
    }
}