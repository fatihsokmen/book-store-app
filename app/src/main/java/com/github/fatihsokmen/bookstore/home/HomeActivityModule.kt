package com.github.fatihsokmen.bookstore.home

import com.github.fatihsokmen.bookstore.dependency.scope.FragmentViewScope
import dagger.Binds
import dagger.Module

@Module
abstract class HomeActivityModule {

    @Binds
    @FragmentViewScope
    abstract fun bindsProductDetailsPresenter(impl: HomeActivityPresenter):
            HomeActivityContract.Presenter
}
