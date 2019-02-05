package com.github.fatihsokmen.payment.sdk.cardpayment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import com.github.fatihsokmen.payment.sdk.R
import com.github.fatihsokmen.payment.sdk.cardpayment.resources.StringResourcesImpl
import android.view.Gravity

class CardPaymentActivity : Activity(), CardPaymentContract.Interactions {

    private lateinit var presenter: CardPaymentContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_payment)

        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window.attributes.apply {
            gravity = Gravity.BOTTOM
        }.also { layoutParams ->
            window.attributes = layoutParams
        }

        /**
         * This mimics Dagger dependency injection (component & modules)
         */
        presenter = CardPaymentPresenter(
            paymentUrl = intent.getStringExtra(PAYMENT_URL_KEY),
            paymentReference = intent.getStringExtra(ORDER_REFERENCE_KEY),
            view = CardPaymentView(findViewById(R.id.bottom_sheet)),
            interactions = this,
            paymentApiInteractor = CardPaymentApiInteractor(CardPaymentService()),
            stringResources = StringResourcesImpl(this),
            dispatchers = CoroutinesDispatcherProvider.Default()
        )
    }

    override fun onPaymentAuthorized() {
        finishWithData(CardPaymentData(CardPaymentData.STATUS_PAYMENT_AUTHORIZED))
    }

    override fun onPaymentFailed() {
        finishWithData(CardPaymentData(CardPaymentData.STATUS_PAYMENT_FAILED))
    }

    override fun onGenericError(message: String?) {
        finishWithData(CardPaymentData(CardPaymentData.STATUS_GENERIC_ERROR, message))
    }

    private fun finishWithData(cardPaymentData: CardPaymentData) {
        val intent = Intent().apply {
            putExtra(CardPaymentData.INTENT_DATA_KEY, cardPaymentData)
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun finish() {
        super.finish()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            overridePendingTransition(0, 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    companion object {
        private const val PAYMENT_URL_KEY = "payment-api-url"
        private const val ORDER_REFERENCE_KEY = "merchant-order-reference"

        fun getIntent(context: Context, paymentUrl: String, merchantOrderReference: String) =
            Intent(context, CardPaymentActivity::class.java).apply {
                putExtra(PAYMENT_URL_KEY, paymentUrl)
                putExtra(ORDER_REFERENCE_KEY, merchantOrderReference)
            }

    }
}
