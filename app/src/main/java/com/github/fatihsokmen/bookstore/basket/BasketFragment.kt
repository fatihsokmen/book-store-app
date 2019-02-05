package com.github.fatihsokmen.bookstore.basket

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import javax.inject.Inject
import android.support.v7.widget.DividerItemDecoration
import com.github.fatihsokmen.bookstore.App
import com.github.fatihsokmen.bookstore.R
import com.github.fatihsokmen.bookstore.basket.data.AmountDetails
import com.github.fatihsokmen.bookstore.basket.data.BasketProductDomain
import com.github.fatihsokmen.bookstore.basket.viewholder.BasketProductViewHolderFactory
import com.github.fatihsokmen.bookstore.home.HomeActivity
import com.github.fatihsokmen.payment.sdk.cardpayment.CardPaymentData

import kotlinx.android.synthetic.main.fragment_basket.*


class BasketFragment : Fragment(), BasketFragmentContract.View {

    @Inject
    internal lateinit var viewHolderFactoryBuilder: BasketProductViewHolderFactory.Builder

    @Inject
    internal lateinit var presenter: BasketFragmentContract.Presenter

    @Inject
    internal lateinit var interactions: BasketFragmentContract.Interactions

    private lateinit var adapter: BasketProductsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_basket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createBasketComponent(this, activity!!).inject(this)

        adapter = BasketProductsAdapter(viewHolderFactoryBuilder, interactions)
        basketProductsView.adapter = adapter
        basketProductsView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        cardPaymentButton.setOnClickListener {
            onCardPaymentClicked()
        }
        continueShoppingButton.setOnClickListener {
            onContinueClicked()
        }
        presenter.init()

    }

    override fun bindData(basketProducts: List<BasketProductDomain>) {
        adapter.setData(basketProducts)
    }

    override fun showBasketEmptyMessage() {
        emptyBasketMessageView.visibility = View.VISIBLE
    }

    override fun showPayButtonLayout(show: Boolean) {
        payButtonLayout.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showAmountLayout(show: Boolean) {
        amountLayout.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showError(message: String?) {
        Snackbar.make(view!!, message ?: getString(R.string.unknown_error), Snackbar.LENGTH_SHORT).show()
    }

    override fun setSubTotalAmount(amount: String) {
        subTotalAmountView.text = amount
    }

    override fun setTaxAmount(amount: String) {
        taxAmountView.text = amount
    }

    override fun showCardPaymentButton(show: Boolean) {
        cardPaymentButton.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showProgress(show: Boolean) {
        progressView.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showSnackBar(message: String?) {
        message?.let {
            Snackbar.make(progressView, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun showOrderSuccessful() {
        bindData(listOf())
        payButtonLayout.visibility = View.GONE
        amountLayout.visibility = View.GONE
        orderSuccessful.visibility = View.VISIBLE
    }

    fun onCardPaymentResponse(data: CardPaymentData) {
        presenter.onCardPaymentResponse(data)
    }

    fun onCardPaymentCancelled() {
        presenter.onCardPaymentCancelled()
    }

    private fun onCardPaymentClicked() {
        presenter.onPayWithCard()
    }

    private fun onContinueClicked() {
        context?.let {
            val intent = Intent(it, HomeActivity::class.java)
            intent.putExtra("tab", R.id.navigate_books)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION or Intent.FLAG_ACTIVITY_CLEAR_TOP
            it.startActivity(intent)
        }
    }

    companion object {

        internal const val CARD_PAYMENT_REQUEST_CODE: Int = 0

        private fun createBasketComponent(fragment: BasketFragment, context: Activity)
                : BasketFragmentComponent {
            val baseComponent = (fragment.activity?.application as App).baseComponent
            return DaggerBasketFragmentComponent
                .builder()
                .amountDetails(AmountDetails())
                .context(context)
                .view(fragment)
                .baseComponent(baseComponent)
                .build()
        }
    }
}
