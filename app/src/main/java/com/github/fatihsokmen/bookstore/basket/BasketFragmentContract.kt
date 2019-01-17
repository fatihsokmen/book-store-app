package com.github.fatihsokmen.bookstore.basket

import com.github.fatihsokmen.bookstore.basket.data.BasketProductDomain
import com.github.fatihsokmen.payment.sdk.cardpayment.CardPaymentData


interface BasketFragmentContract {

    interface View {
        fun bindData(basketProducts: List<BasketProductDomain>)

        fun showError(message: String?)

        fun showBasketEmptyMessage()

        fun showPayButtonLayout(show: Boolean)

        fun showAmountLayout(show: Boolean)

        fun showProgress(show: Boolean)

        fun showSnackBar(message: String?)

        fun setSubTotalAmount(amount: String)

        fun setTaxAmount(amount: String)

        fun showCardPaymentButton(show: Boolean)

        fun showOrderSuccessful()
    }

    interface Presenter {
        fun init()

        fun cleanup()

        fun deleteProduct(id: String)

        fun onPayWithCard()

        fun onCardPaymentResponse(data: CardPaymentData)

        fun onCardPaymentCancelled()
    }

    interface Interactions {

        fun onDeleteClicked(id: String)
    }
}
