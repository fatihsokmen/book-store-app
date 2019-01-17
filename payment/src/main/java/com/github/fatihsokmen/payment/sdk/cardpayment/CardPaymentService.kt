package com.github.fatihsokmen.payment.sdk.cardpayment

import kotlinx.coroutines.delay

/**
 * This service mimics Retrofit call, instead we are using a suspend function for coroutines
 */
class CardPaymentService {

    @Suppress("UNUSED_PARAMETER")
    suspend fun authorizePayment(
        paymentReference: String,
        pan: String,
        expiry: String,
        cvv: String,
        cardHolder: String
    ): String {
        delay(timeMillis = 2000)
        return "AUTHORISED"
    }
}