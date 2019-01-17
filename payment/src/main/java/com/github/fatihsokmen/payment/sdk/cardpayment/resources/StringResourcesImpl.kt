package com.github.fatihsokmen.payment.sdk.cardpayment.resources

import android.content.Context
import android.support.annotation.StringRes

class StringResourcesImpl constructor(private val context: Context) : StringResources {

    override fun getString(@StringRes resourceId: Int): String =
        context.getString(resourceId)

    override fun getString(resourceId: Int, vararg formatArgs: Any): String =
        context.getString(resourceId, *formatArgs)
}