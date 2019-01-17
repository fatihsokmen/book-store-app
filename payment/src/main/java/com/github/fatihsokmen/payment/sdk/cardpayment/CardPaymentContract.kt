package com.github.fatihsokmen.payment.sdk.cardpayment


interface CardPaymentContract {

    interface View {

        val pan: String

        val expiry: String

        val cvv: String

        val cardHolder: String

        fun setPresenter(presenter: CardPaymentContract.Presenter)

        fun setFloatingHintText(text: String)

        fun showProgress(show: Boolean, text: String? = null)

        fun showError(message: String)
    }

    interface Presenter {
        fun onCardNumberFocusGained()

        fun onExpireDateFocusGained()

        fun onCvvFocusGained()

        fun onPayClicked()
    }

    interface Interactions {

        fun onPaymentAuthorized()

        fun onPaymentFailed()

        fun onGenericError(message: String?)
    }
}
