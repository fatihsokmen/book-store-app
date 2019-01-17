package com.github.fatihsokmen.bookstore.dependency

import android.app.Application
import android.arch.persistence.room.Room
import com.github.fatihsokmen.bookstore.dependency.repository.ProductDao
import com.github.fatihsokmen.bookstore.dependency.repository.ProductDatabase
import com.github.fatihsokmen.bookstore.dependency.repository.ProductRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @Singleton
        fun provideProductDatabase(app: Application): ProductDatabase =
            Room.databaseBuilder(
                app.applicationContext, ProductDatabase::class.java, "product_db"
            ).fallbackToDestructiveMigration().build()

        @JvmStatic
        @Provides
        @Singleton
        fun provideProductDao(database: ProductDatabase): ProductDao =
            database.productDao()

        @JvmStatic
        @Provides
        @Singleton
        fun provideProductRepository(repository: ProductRepository): ProductRepository {
            return repository
        }
    }
}
