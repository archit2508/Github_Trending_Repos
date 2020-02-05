package com.example.top_github.imageCaching.async

import java.util.concurrent.Callable

/**
 * Acts as base class for any download task which needs a new thread to perform download operation
 */
abstract class DownloadTask<T> : Callable<T> {
    abstract fun download(url: String): T
}