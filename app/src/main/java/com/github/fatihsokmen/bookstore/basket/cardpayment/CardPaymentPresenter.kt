package com.github.fatihsokmen.bookstore.basket.cardpayment

import android.annotation.SuppressLint
import com.github.fatihsokmen.bookstore.basket.BasketFragment
import com.github.fatihsokmen.bookstore.basket.BasketFragmentContract
import com.github.fatihsokmen.bookstore.basket.data.AmountDetails
import com.github.fatihsokmen.bookstore.basket.data.PaymentOrderApiInteractor
import com.github.fatihsokmen.bookstore.dependency.scheduler.Scheduler
import com.github.fatihsokmen.payment.sdk.PaymentClient
import com.github.fatihsokmen.payment.sdk.cardpayment.CardPaymentRequest
import io.reactivex.disposables.CompositeDisposable
import java.math.BigDecimal
import javax.inject.Inject

class CardPaymentPresenter @Inject constructor(
    private val view: BasketFragmentContract.View,
    private val orderApiInteractor: PaymentOrderApiInteractor,
    private val paymentClient: PaymentClient,
    private val scheduler: Scheduler,
    private val amountDetails: AmountDetails
) {
    private val subscriptions = CompositeDisposable()

    @SuppressLint("CheckResult")
    fun launchCardPayment() {
        view.showProgress(true)
        subscriptions.add(
            orderApiInteractor.createPaymentOrder(
                amount = totalAmount(),
                currency = amountDetails.getCurrency().currencyCode
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.main())
                .subscribe({ orderInfo ->
                    view.showProgress(false)
                    paymentClient.launchCardPayment(
                        request = CardPaymentRequest.builder()
                            .paymentUrl(orderInfo.paymentUrl)
                            .orderReference(orderInfo.orderReference)
                            .build(),
                        requestCode = BasketFragment.CARD_PAYMENT_REQUEST_CODE
                    )
                }, { error ->
                    view.showProgress(false)
                    view.showSnackBar(error.message)
                })
        )
    }

    private fun totalAmount() =
        amountDetails.getItems().map { it.amount }.fold(BigDecimal.ZERO) { acc, e -> acc + e }

    fun cleanup() {
        subscriptions.dispose()
    }
}
