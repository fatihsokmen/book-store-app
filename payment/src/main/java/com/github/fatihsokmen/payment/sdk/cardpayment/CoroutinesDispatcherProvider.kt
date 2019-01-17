package com.github.fatihsokmen.payment.sdk.cardpayment

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface CoroutinesDispatcherProvider {
    val main: CoroutineContext
        get() = Dispatchers.Main
    val io: CoroutineContext
        get() = Dispatchers.IO

    class Default : CoroutinesDispatcherProvider
}