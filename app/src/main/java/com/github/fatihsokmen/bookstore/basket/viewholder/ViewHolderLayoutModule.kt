package com.github.fatihsokmen.bookstore.basket.viewholder

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.fatihsokmen.bookstore.dependency.scope.ViewHolderScope
import dagger.Module
import dagger.Provides

@Module
class ViewHolderLayoutModule(@LayoutRes private val layoutId: Int) {

    @Provides
    @ViewHolderScope
    fun provideItemView(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    }
}