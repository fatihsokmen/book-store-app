package com.github.fatihsokmen.payment.sdk.cardpayment.resources

import android.support.annotation.StringRes

interface StringResources {

    fun getString(@StringRes resourceId: Int): String

    fun getString(@StringRes resourceId: Int, vararg formatArgs: Any): String
}