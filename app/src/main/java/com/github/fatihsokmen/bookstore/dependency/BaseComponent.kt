package com.github.fatihsokmen.bookstore.dependency


import android.app.Application
import android.content.Context
import com.github.fatihsokmen.bookstore.dependency.formatter.Formatter
import com.github.fatihsokmen.bookstore.dependency.repository.ProductDao
import com.github.fatihsokmen.bookstore.dependency.resource.AssetResources
import com.github.fatihsokmen.bookstore.dependency.resource.StringResources
import com.github.fatihsokmen.bookstore.dependency.scheduler.Scheduler


import dagger.BindsInstance
import okhttp3.OkHttpClient

interface BaseComponent {

    fun applicationContext(): Context

    fun scheduler(): Scheduler

    fun stringResources(): StringResources

    fun productDao(): ProductDao

    fun okHttpClient(): OkHttpClient

    fun formatter(): Formatter

    fun assetResources(): AssetResources

    interface Builder<C : BaseComponent, B : Builder<C, B>> {

        @BindsInstance
        fun application(application: Application): B

        fun build(): C
    }
}
