package com.github.fatihsokmen.bookstore


import android.support.multidex.MultiDexApplication
import android.support.v7.app.AppCompatDelegate
import com.github.fatihsokmen.bookstore.dependency.AppComponent
import com.github.fatihsokmen.bookstore.dependency.BaseComponent
import com.github.fatihsokmen.bookstore.dependency.DaggerAppComponent

class App : MultiDexApplication() {

    lateinit var baseComponent: BaseComponent

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        baseComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()
        (baseComponent as AppComponent).inject(this)
    }
}