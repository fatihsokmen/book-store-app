package com.github.fatihsokmen.bookstore.basket


import android.app.Activity
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
import retrofit2.Retrofit

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
        fun provideMerchantApiService(retrofit: Retrofit): MerchantApiService {
            return retrofit.create(MerchantApiService::class.java)
        }

        @JvmStatic
        @Provides
        @FragmentViewScope
        fun providePaymentClient(context: Activity) = PaymentClient(context)

    }
}
