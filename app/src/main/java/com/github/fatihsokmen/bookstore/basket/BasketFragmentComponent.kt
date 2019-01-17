package com.github.fatihsokmen.bookstore.basket

import android.app.Activity
import com.github.fatihsokmen.bookstore.basket.data.AmountDetails
import com.github.fatihsokmen.bookstore.dependency.BaseComponent
import com.github.fatihsokmen.bookstore.dependency.scope.FragmentViewScope
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [BaseComponent::class], modules = [BasketFragmentModule::class])
@FragmentViewScope
interface BasketFragmentComponent {

    fun inject(fragment: BasketFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun view(view: BasketFragmentContract.View): Builder

        @BindsInstance
        fun context(context: Activity): Builder

        @BindsInstance
        fun amountDetails(amountDetails: AmountDetails): Builder

        fun baseComponent(baseComponent: BaseComponent): Builder

        fun build(): BasketFragmentComponent
    }
}