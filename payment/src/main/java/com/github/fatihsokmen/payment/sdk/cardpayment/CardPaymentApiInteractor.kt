package com.github.fatihsokmen.payment.sdk.cardpayment

internal class CardPaymentApiInteractor constructor(
    private val paymentService: CardPaymentService
) {
    private lateinit var paymentUrl: String

    fun init(paymentUrl: String) {
        this.paymentUrl = paymentUrl
    }

    /**
     * Different from demo app, we are not using RxJava but Kotlin Coroutines
     * Result is sent back using lambda functions
     */
    suspend fun authorizePayment(
        paymentReference: String,
        pan: String,
        expiry: String,
        cvv: String,
        cardHolder: String
    ): String {
        return paymentService.authorizePayment(paymentReference, pan, expiry, cvv, cardHolder)
    }
}
