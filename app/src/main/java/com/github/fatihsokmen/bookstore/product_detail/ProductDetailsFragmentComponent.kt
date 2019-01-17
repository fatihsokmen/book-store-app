package com.github.fatihsokmen.bookstore.product_detail

import com.github.fatihsokmen.bookstore.dependency.BaseComponent
import com.github.fatihsokmen.bookstore.dependency.scope.FragmentViewScope
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [BaseComponent::class], modules = [ProductDetailsFragmentModule::class])
@FragmentViewScope
interface ProductDetailsFragmentComponent {

    fun inject(fragment: ProductDetailsFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun view(view: ProductDetailsFragmentContract.View): Builder

        fun baseComponent(baseComponent: BaseComponent): Builder

        fun build(): ProductDetailsFragmentComponent
    }
}