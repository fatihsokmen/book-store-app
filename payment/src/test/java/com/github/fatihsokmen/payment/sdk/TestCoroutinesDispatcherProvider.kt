package com.github.fatihsokmen.payment.sdk

import com.github.fatihsokmen.payment.sdk.cardpayment.CoroutinesDispatcherProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.coroutines.CoroutineContext

class TestCoroutinesDispatcherProvider : CoroutinesDispatcherProvider {

    @ExperimentalCoroutinesApi
    override val main: CoroutineContext
        get() = Dispatchers.Unconfined

    @ExperimentalCoroutinesApi
    override val io: CoroutineContext
        get() = Dispatchers.Unconfined

}