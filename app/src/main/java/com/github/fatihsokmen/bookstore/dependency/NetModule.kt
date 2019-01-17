package com.github.fatihsokmen.bookstore.dependency

import android.app.Application
import com.github.fatihsokmen.bookstore.dependency.mockapi.OkHttpMockInterceptor
import com.github.fatihsokmen.bookstore.dependency.resource.AssetResources
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object NetModule {

    @JvmStatic
    @Provides
    @Singleton
    fun provideHttpCache(app: Application) =
        Cache(app.cacheDir, (10 * 1024 * 1024).toLong())

    @JvmStatic
    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache, mockResponseInterceptor: Interceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

        return OkHttpClient.Builder().apply {
            addInterceptor(mockResponseInterceptor)
            addInterceptor(loggingInterceptor)
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            cache(cache)
        }.build()
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideMockOkHttpResponseInterceptor(
        assetResources: AssetResources
    ): Interceptor =
        OkHttpMockInterceptor(assetResources)
}