package com.github.fatihsokmen.payment.sdk.cardpayment

import android.app.AlertDialog
import android.support.design.widget.Snackbar
import android.support.v7.widget.AppCompatEditText
import android.view.View
import android.widget.*
import com.github.fatihsokmen.payment.sdk.R
import com.github.fatihsokmen.payment.sdk.cardpayment.widget.FloatingHintView


internal class CardPaymentView constructor(
    private val root: View
) : CardPaymentContract.View, View.OnFocusChangeListener {

    private val floatingHint: FloatingHintView =
        root.findViewById(R.id.floating_hint_view)
    private val cardNumberEdit: AppCompatEditText =
        root.findViewById(R.id.edit_card_number)
    private val cardExpireEdit: AppCompatEditText =
        root.findViewById(R.id.edit_expire_date)
    private val cardCvvEdit: AppCompatEditText =
        root.findViewById(R.id.edit_cvv)
    private val cardHolderEdit: EditText =
        root.findViewById(R.id.edit_card_holder)
    private val payButton: Button =
        root.findViewById<Button>(R.id.pay_button).apply {
            setOnClickListener {
                presenter.onPayClicked()
            }
        }
    override val pan: String
        get() = cardNumberEdit.text.toString()

    override val expiry: String
        get() = cardExpireEdit.text.toString()

    override val cvv: String
        get() = cardCvvEdit.text.toString()

    override val cardHolder: String
        get() = cardHolderEdit.text.toString()

    private lateinit var presenter: CardPaymentContract.Presenter

    private var progressDialog: AlertDialog? = null

    init {
        cardNumberEdit.onFocusChangeListener = this
        cardExpireEdit.onFocusChangeListener = this
        cardCvvEdit.onFocusChangeListener = this
        setFloatingHintText(root.context.getString(R.string.hint_card_number))
    }

    override fun setPresenter(presenter: CardPaymentContract.Presenter) {
        this.presenter = presenter
    }

    override fun setFloatingHintText(text: String) {
        floatingHint.text = text
    }

    override fun onFocusChange(view: View, hasFocus: Boolean) {
        if (hasFocus) {
            when (view.id) {
                R.id.edit_card_number -> presenter.onCardNumberFocusGained()
                R.id.edit_expire_date -> presenter.onExpireDateFocusGained()
                R.id.edit_cvv -> presenter.onCvvFocusGained()
            }
            floatingHint.animateToAlignViewStart(view)
        }
    }

    override fun showError(message: String) {
        Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun showProgress(show: Boolean, text: String?) {
        progressDialog = if (show) {
            progressDialog?.dismiss()
            AlertDialog.Builder(root.context)
                .setTitle(null)
                .setCancelable(false)
                .create().apply {
                    show()
                    setContentView(R.layout.view_progress_dialog)
                    findViewById<TextView>(R.id.text).text = text
                }
        } else {
            progressDialog?.dismiss()
            null
        }
    }
}
