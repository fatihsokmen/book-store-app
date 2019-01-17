package com.github.fatihsokmen.payment.sdk.cardpayment


class CardPaymentRequest private constructor(
    val paymentUrl: String,
    val orderReference: String
) {

    class Builder {
        private var paymentUrl: String? = null
        private var orderReference: String? = null

        fun paymentUrl(url: String) = this.apply {
            paymentUrl = url
        }

        fun orderReference(order: String) = this.apply {
            orderReference = order
        }

        fun build(): CardPaymentRequest {
            requireNotNull(paymentUrl) {
                "Payment url should not be null"
            }
            requireNotNull(orderReference) {
                "Merchant order reference should not be null"
            }
            return CardPaymentRequest(paymentUrl!!, orderReference!!)
        }
    }

    companion object {
        fun builder() = Builder()
    }

}