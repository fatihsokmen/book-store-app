package com.github.fatihsokmen.bookstore.dependency


import android.app.Application
import android.content.Context
import com.github.fatihsokmen.bookstore.dependency.formatter.Formatter
import com.github.fatihsokmen.bookstore.dependency.formatter.FormatterImpl
import com.github.fatihsokmen.bookstore.dependency.resource.AssetResources
import com.github.fatihsokmen.bookstore.dependency.resource.AssetResourcesImpl
import com.github.fatihsokmen.bookstore.dependency.resource.StringResources
import com.github.fatihsokmen.bookstore.dependency.resource.StringResourcesImpl
import com.github.fatihsokmen.bookstore.dependency.scheduler.Scheduler
import com.github.fatihsokmen.bookstore.dependency.scheduler.SchedulerImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindScheduler(impl: SchedulerImpl): Scheduler

    @Binds
    @Singleton
    abstract fun bindApplication(impl: Application): Context

    @Binds
    @Singleton
    abstract fun bindStringResource(impl: StringResourcesImpl): StringResources

    @Binds
    @Singleton
    abstract fun bindFormatter(impl: FormatterImpl): Formatter

    @Binds
    @Singleton
    abstract fun bindAssets(impl: AssetResourcesImpl): AssetResources


}
