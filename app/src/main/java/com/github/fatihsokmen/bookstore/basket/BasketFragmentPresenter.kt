package com.github.fatihsokmen.bookstore.basket


import com.github.fatihsokmen.bookstore.basket.cardpayment.CardPaymentPresenter
import com.github.fatihsokmen.bookstore.basket.data.AmountDetails
import com.github.fatihsokmen.bookstore.dependency.formatter.Formatter
import com.github.fatihsokmen.bookstore.dependency.repository.ProductRepository
import com.github.fatihsokmen.bookstore.dependency.scheduler.Scheduler
import com.github.fatihsokmen.payment.sdk.cardpayment.CardPaymentData
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

class BasketFragmentPresenter @Inject constructor(
    private val view: BasketFragmentContract.View,
    private val cardPaymentPresenter: CardPaymentPresenter,
    private val repository: ProductRepository,
    private val scheduler: Scheduler,
    private val formatter: Formatter,
    private val amountDetails: AmountDetails
) : BasketFragmentContract.Presenter {

    private val subscriptions = CompositeDisposable()

    override fun init() {
        val disposable = repository.getBasketProducts().take(1)
            .flatMapSingle { basketProducts ->
                val priceSum = basketProducts.map { it.price.total.multiply(it.amount.toBigDecimal()) }
                    .fold(BigDecimal.ZERO) { acc, e -> acc + e }
                val taxSum = basketProducts.map { it.price.tax.multiply(it.amount.toBigDecimal()) }
                    .fold(BigDecimal.ZERO) { acc, e -> acc + e }

                return@flatMapSingle Single.just(Pair(basketProducts, Pair(priceSum, taxSum)))
            }
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.main())
            .subscribe({ pair ->
                amountDetails.reset() // Reset all details

                if (pair.first.isEmpty()) {
                    view.showBasketEmptyMessage()
                    view.bindData(emptyList())
                    view.showPayButtonLayout(false)
                    view.showAmountLayout(false)
                } else {
                    val (subTotalSum, taxSum) = pair.second
                    val currency = pair.first[0].price.currency
                    with(amountDetails) {
                        setTotal(subTotalSum.add(taxSum), currency)
                        addItem("subTotal", "Sub Total", subTotalSum)
                        addItem("tax", "Tax", taxSum)
                    }
                    view.bindData(pair.first)
                    view.showPayButtonLayout(true)
                    view.showAmountLayout(true)
                    view.setSubTotalAmount(formatter.formatAmount(amountDetails.getCurrency(), subTotalSum, Locale.US))
                    view.setTaxAmount(formatter.formatAmount(amountDetails.getCurrency(), taxSum, Locale.US))
                }
            }, { error ->
                view.showError(error.message)
            })
        subscriptions.add(disposable)

    }

    override fun onPayWithCard() {
        cardPaymentPresenter.launchCardPayment()
    }

    override fun onCardPaymentResponse(data: CardPaymentData) {
        when (data.code) {
            CardPaymentData.STATUS_PAYMENT_AUTHORIZED -> {
                repository.removeAll()
                    .subscribeOn(scheduler.io())
                    .observeOn(scheduler.main())
                    .subscribe()
                view.showOrderSuccessful()
            }
            CardPaymentData.STATUS_PAYMENT_FAILED -> {
                view.showError("Payment failed")
            }
            CardPaymentData.STATUS_GENERIC_ERROR -> {
                view.showError("Generic error(${data.reason})")
            }
            else -> IllegalArgumentException("Unknown payment response (${data.reason})")
        }
    }

    override fun onCardPaymentCancelled() {
        view.showSnackBar("Payment cancelled")
    }

    override fun deleteProduct(id: String) {
        subscriptions.add(
            repository.deleteProduct(id)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.main())
                .doOnComplete {
                    init()
                }.subscribe()
        )
    }

    override fun cleanup() {
        subscriptions.dispose()
        cardPaymentPresenter.cleanup()
    }

}
