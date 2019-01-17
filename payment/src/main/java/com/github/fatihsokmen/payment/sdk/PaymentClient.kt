package com.github.fatihsokmen.payment.sdk

import android.app.Activity
import com.github.fatihsokmen.payment.sdk.cardpayment.CardPaymentActivity
import com.github.fatihsokmen.payment.sdk.cardpayment.CardPaymentRequest

class PaymentClient(private val context: Activity) {

    fun launchCardPayment(request: CardPaymentRequest, requestCode: Int) {
        context.startActivityForResult(
            CardPaymentActivity.getIntent(
                context = context,
                paymentUrl = request.paymentUrl,
                merchantOrderReference = request.paymentUrl
            ), requestCode
        )
    }
}