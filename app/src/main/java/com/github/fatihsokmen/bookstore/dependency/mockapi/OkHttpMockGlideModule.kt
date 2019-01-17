package com.github.fatihsokmen.bookstore.dependency.mockapi

import android.content.Context

import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.Excludes
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.github.fatihsokmen.bookstore.App

import java.io.InputStream


@Excludes(OkHttpLibraryGlideModule::class)
@GlideModule
class OkHttpMockGlideModule : AppGlideModule() {

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)

        (context.applicationContext as App).baseComponent.okHttpClient().let { okHttpClient ->
            registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(okHttpClient))
        }
    }
}