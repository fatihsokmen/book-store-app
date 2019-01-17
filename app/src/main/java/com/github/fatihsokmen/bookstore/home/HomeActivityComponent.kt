package com.github.fatihsokmen.bookstore.home

import com.github.fatihsokmen.bookstore.dependency.BaseComponent
import com.github.fatihsokmen.bookstore.dependency.scope.FragmentViewScope
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [BaseComponent::class], modules = [HomeActivityModule::class])
@FragmentViewScope
interface HomeActivityComponent {

    fun inject(fragment: HomeActivity)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun view(view: HomeActivityContract.View): Builder

        fun baseComponent(baseComponent: BaseComponent): Builder

        fun build(): HomeActivityComponent
    }
}