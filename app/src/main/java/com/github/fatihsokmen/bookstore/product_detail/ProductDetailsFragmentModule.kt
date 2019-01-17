package com.github.fatihsokmen.bookstore.product_detail

import com.github.fatihsokmen.bookstore.dependency.scope.FragmentViewScope
import dagger.Binds
import dagger.Module

@Module
abstract class ProductDetailsFragmentModule {

    @Binds
    @FragmentViewScope
    abstract fun bindsProductDetailsPresenter(impl: ProductDetailsFragmentPresenter):
            ProductDetailsFragmentContract.Presenter
}
