package com.github.fatihsokmen.bookstore.basket


import android.app.Activity
import com.github.fatihsokmen.bookstore.BuildConfig
import com.github.fatihsokmen.bookstore.R
import com.github.fatihsokmen.bookstore.basket.data.MerchantApiService
import com.github.fatihsokmen.bookstore.basket.viewholder.BasketProductViewHolderFactory
import com.github.fatihsokmen.bookstore.basket.viewholder.DaggerBasketProductViewHolderFactory
import com.github.fatihsokmen.bookstore.basket.viewholder.ViewHolderLayoutModule
import com.github.fatihsokmen.bookstore.dependency.BaseComponent
import com.github.fatihsokmen.bookstore.dependency.scope.FragmentViewScope
import com.github.fatihsokmen.payment.sdk.PaymentClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
abstract class BasketFragmentModule {

    @Binds
    @FragmentViewScope
    abstract fun bindsBasketPresenter(impl: BasketFragmentPresenter):
            BasketFragmentContract.Presenter

    @Binds
    @FragmentViewScope
    abstract fun bindsViewHolderInteractions(impl: BasketProductViewHolderInteractions):
            BasketFragmentContract.Interactions

    @Module
    companion object {

        @JvmStatic
        @Provides
        @FragmentViewScope
        fun provideViewHolderFactoryBuilder(baseComponent: BaseComponent): BasketProductViewHolderFactory.Builder {
            return DaggerBasketProductViewHolderFactory
                .builder()
                .baseComponent(baseComponent)
                .layoutModule(ViewHolderLayoutModule(R.layout.view_basket_product_item))
        }

        @JvmStatic
        @Provides
        @FragmentViewScope
        fun provideMerchantRetrofit(okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                .baseUrl(BuildConfig.MERCHANT_SERVER_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        @JvmStatic
        @Provides
        @FragmentViewScope
        fun provideMerchantApiService(retrofit: Retrofit): MerchantApiService {
            return retrofit.create(MerchantApiService::class.java)
        }

        @JvmStatic
        @Provides
        @FragmentViewScope
        fun providePaymentClient(context: Activity) = PaymentClient(context)

    }
}
