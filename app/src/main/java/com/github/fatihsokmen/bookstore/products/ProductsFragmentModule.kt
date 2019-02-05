package com.github.fatihsokmen.bookstore.products

import com.github.fatihsokmen.bookstore.dependency.formatter.Formatter
import com.github.fatihsokmen.bookstore.dependency.scope.FragmentViewScope
import com.github.fatihsokmen.bookstore.products.data.ProductApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
abstract class ProductsFragmentModule {

    @Binds
    @FragmentViewScope
    abstract fun bindsProductsPresenter(impl: ProductsFragmentPresenter):
            ProductsFragmentContract.Presenter

    @Module
    companion object {

        @JvmStatic
        @Provides
        @FragmentViewScope
        fun provideProductsService(retrofit: Retrofit): ProductApiService {
            return retrofit.create(ProductApiService::class.java)
        }

        @JvmStatic
        @Provides
        @FragmentViewScope
        fun provideProductAdapter(formatter: Formatter) = ProductsAdapter(formatter)
    }
}
