package com.github.fatihsokmen.bookstore.basket.viewholder


import android.view.ViewGroup
import com.github.fatihsokmen.bookstore.basket.BasketFragmentContract
import com.github.fatihsokmen.bookstore.dependency.BaseComponent
import com.github.fatihsokmen.bookstore.dependency.scope.ViewHolderScope
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [BaseComponent::class], modules = [ViewHolderLayoutModule::class])
@ViewHolderScope
interface BasketProductViewHolderFactory {

    fun createViewHolder(): BasketProductViewHolderView

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun interactions(interactions: BasketFragmentContract.Interactions): Builder

        @BindsInstance
        fun parentView(parentView: ViewGroup): Builder

        fun baseComponent(baseComponent: BaseComponent): Builder

        fun layoutModule(layoutModule: ViewHolderLayoutModule): Builder

        fun build(): BasketProductViewHolderFactory
    }
}