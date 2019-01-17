package com.github.fatihsokmen.bookstore.dependency.resource

import android.support.annotation.StringRes

interface StringResources {

    fun getString(@StringRes resourceId: Int) : String

    fun getString(@StringRes resourceId: Int, vararg formatArgs: Any): String
}