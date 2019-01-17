package com.github.fatihsokmen.payment.sdk.cardpayment

import android.support.annotation.VisibleForTesting
import com.github.fatihsokmen.payment.sdk.R
import com.github.fatihsokmen.payment.sdk.cardpayment.resources.StringResources
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class CardPaymentPresenter(
    paymentUrl: String,
    private val paymentReference: String,
    private val view: CardPaymentContract.View,
    private val interactions: CardPaymentContract.Interactions,
    private val paymentApiInteractor: CardPaymentApiInteractor,
    private val stringResources: StringResources,
    private val dispatchers: CoroutinesDispatcherProvider
) : CardPaymentContract.Presenter {

    private val expiryMatcher: Regex by lazy {
        Regex("^\\d{2}\\/\\d{2}\$")
    }

    init {
        view.setPresenter(this)
        paymentApiInteractor.init(paymentUrl)
    }

    override fun onCardNumberFocusGained() {
        view.setFloatingHintText(stringResources.getString(CARD_NUMBER_LABEL_RESOURCE))
    }

    override fun onExpireDateFocusGained() {
        view.setFloatingHintText(stringResources.getString(CARD_EXPIRY_LABEL_RESOURCE))
    }

    override fun onCvvFocusGained() {
        view.setFloatingHintText(stringResources.getString(CVV_LABEL_RESOURCE))
    }

    override fun onPayClicked() {
        validateCardData()?.let { error ->
            view.showError(error)
        } ?: doAuthorizePayment()
    }

    private fun doAuthorizePayment() {
        view.showProgress(true, stringResources.getString(PROGRESS_MESSAGE_RESOURCE))
        GlobalScope.launch(dispatchers.main) {
            val response = withContext(dispatchers.io) {
                paymentApiInteractor.authorizePayment(
                    paymentReference,
                    view.pan,
                    view.expiry,
                    view.cvv,
                    view.cardHolder
                )
            }
            onPaymentAuthorizationResponse(response)
        }
    }

    private fun validateCardData(): String? {
        if (view.pan.isEmpty() || view.pan.length < 16) {
            return stringResources.getString(ERROR_EMPTY_PAN_RESOURCE)
        }
        if (view.expiry.isEmpty() || !expiryMatcher.matches(view.expiry)) {
            return stringResources.getString(ERROR_EMPTY_EXPIRY_RESOURCE)
        }
        if (view.cvv.isEmpty() || view.cvv.length < 3) {
            return stringResources.getString(ERROR_EMPTY_CVV_RESOURCE)
        }
        if (view.cardHolder.isEmpty()) {
            return stringResources.getString(ERROR_EMPTY_CARDHOLDER_RESOURCE)
        }
        return null
    }

    private fun onPaymentAuthorizationResponse(response: String) {
        view.showProgress(false)
        when (response) {
            STATUS_PAYMENT_AUTHORISED -> interactions.onPaymentAuthorized()
            STATUS_PAYMENT_FAILED -> interactions.onPaymentFailed()
            else -> interactions.onGenericError("Unknown payment state: $response")
        }
    }

    companion object {
        private const val STATUS_PAYMENT_AUTHORISED = "AUTHORISED"
        private const val STATUS_PAYMENT_FAILED = "FAILED"

        @VisibleForTesting
        internal val PROGRESS_MESSAGE_RESOURCE: Int = R.string.submitting_payment_message
        @VisibleForTesting
        internal val CARD_NUMBER_LABEL_RESOURCE: Int = R.string.hint_card_number
        @VisibleForTesting
        internal val CARD_EXPIRY_LABEL_RESOURCE: Int = R.string.hint_expiry_date
        @VisibleForTesting
        internal val CVV_LABEL_RESOURCE: Int = R.string.hint_cvv

        @VisibleForTesting
        internal val ERROR_EMPTY_PAN_RESOURCE: Int = R.string.error_pan_cant_be_empty
        @VisibleForTesting
        internal val ERROR_EMPTY_EXPIRY_RESOURCE: Int = R.string.error_expiry_cant_be_empty
        @VisibleForTesting
        internal val ERROR_EMPTY_CVV_RESOURCE: Int = R.string.error_cvv_cant_be_empty
        @VisibleForTesting
        internal val ERROR_EMPTY_CARDHOLDER_RESOURCE: Int = R.string.error_cardholder_cant_be_empty
    }
}


