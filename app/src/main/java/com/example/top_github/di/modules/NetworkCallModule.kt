package com.example.top_github.di.modules

import android.app.Application
import com.example.top_github.data.interceptor.OfflineCacheInterceptor
import com.example.top_github.data.interceptor.OnlineCacheInterceptor
import com.example.top_github.data.model.TrendingRepos
import com.example.top_github.data.remoteRepo.TrendingReposRepo
import com.example.top_github.data.service.TrendingReposService
import com.example.top_github.utils.AppConstants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import javax.inject.Singleton
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Provides all network call related dependencies
 */
@Module
class NetworkCallModule {

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    internal fun provideCache(application: Application): Cache {
        val cacheSize = (10 * 1024 * 1024).toLong()
        val httpCacheDirectory = File(application.cacheDir, "http-cache")
        return Cache(httpCacheDirectory, cacheSize)
    }


    @Provides
    @Singleton
    internal fun provideOnlineCacheInterceptor(application: Application): OnlineCacheInterceptor {
        return OnlineCacheInterceptor(application.applicationContext)
    }

    @Provides
    @Singleton
    internal fun provideOfflineCacheInterceptor(application: Application): OfflineCacheInterceptor {
        return OfflineCacheInterceptor(application.applicationContext)
    }

    @Provides
    @Singleton
    internal fun provideOkhttpClient(cache: Cache, onlineCacheInterceptor: OnlineCacheInterceptor, offlineCacheInterceptor: OfflineCacheInterceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.cache(cache)
        httpClient.addInterceptor(offlineCacheInterceptor)
        httpClient.addNetworkInterceptor(onlineCacheInterceptor)
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        return httpClient.build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(AppConstants.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    internal fun provideTrendingReposService(retrofit: Retrofit): TrendingReposService {
        return retrofit.create(TrendingReposService::class.java)
    }

    @Provides
    internal fun provideTrendingReposRepo(service: TrendingReposService): TrendingReposRepo {
        return TrendingReposRepo(service)
    }
}