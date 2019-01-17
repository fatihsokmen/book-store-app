package com.github.fatihsokmen.bookstore.dependency

import com.github.fatihsokmen.bookstore.App
import dagger.Component
import javax.inject.Singleton

@Component(modules = [
    AppModule::class,
    NetModule::class,
    RepositoryModule::class
])
@Singleton
interface AppComponent : BaseComponent {

    fun inject(app: App)

    @Component.Builder
    interface Builder : BaseComponent.Builder<AppComponent, Builder>
}
