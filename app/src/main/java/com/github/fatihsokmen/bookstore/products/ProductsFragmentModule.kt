package com.github.fatihsokmen.bookstore.products

import com.github.fatihsokmen.bookstore.BuildConfig
import com.github.fatihsokmen.bookstore.dependency.formatter.Formatter
import com.github.fatihsokmen.bookstore.dependency.scope.FragmentViewScope
import com.github.fatihsokmen.bookstore.products.data.ProductApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


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


        @JvmStatic
        @Provides
        @FragmentViewScope
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                .baseUrl(BuildConfig.MERCHANT_SERVER_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
    }
}
