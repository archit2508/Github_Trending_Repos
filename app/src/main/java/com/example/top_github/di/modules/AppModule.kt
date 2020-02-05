package com.example.top_github.di.modules

import android.app.Application
import com.example.top_github.imageCaching.core.ImageManager
import com.example.top_github.utils.AppConstants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Provides global dependencies
 */
@Module
class AppModule {

    @Singleton
    @Provides
    fun getImageManager(context: Application): ImageManager{
        return ImageManager.getInstance(context, AppConstants.CACHE_SIZE)
    }
}