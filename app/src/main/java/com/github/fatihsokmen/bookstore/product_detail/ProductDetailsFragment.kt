package com.github.fatihsokmen.bookstore.product_detail

import com.github.fatihsokmen.bookstore.R
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.github.fatihsokmen.bookstore.App
import com.github.fatihsokmen.bookstore.products.data.ProductDomain
import javax.inject.Inject

import kotlinx.android.synthetic.main.fragment_product_details.*


class ProductDetailsFragment : Fragment(), ProductDetailsFragmentContract.View {

    @Inject
    internal lateinit var presenter: ProductDetailsFragmentContract.Presenter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_product_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createProductDetailsComponent(this).inject(this)

        val product: ProductDomain? = activity?.intent?.getParcelableExtra(ProductDetailActivity.KEY_PRODUCT)

        addToBasketButton.setOnClickListener {
            onAddToBasketClicked()
        }
        presenter.init(product!!)

    }

    override fun bindData(product: ProductDomain, productInBasket: Boolean) {
        productName.text = product.name
        productDescription.text = product.description
        addToBasketButton.visibility = if (!productInBasket) View.VISIBLE else View.GONE
        alreadyInBasketMessageView.visibility = if (productInBasket) View.VISIBLE else View.GONE
        Glide.with(this).load(product.imageUrl).into(productImageView)
    }

    override fun dismiss() {
        activity?.onBackPressed()
    }

    override fun onDestroyView() {
        presenter.cleanup()
        super.onDestroyView()
    }

    private fun onAddToBasketClicked() {
        presenter.addToBasket()
    }

    companion object {
        private fun createProductDetailsComponent(fragment: ProductDetailsFragment)
                : ProductDetailsFragmentComponent {
            val baseComponent = (fragment.activity?.application as App).baseComponent
            return DaggerProductDetailsFragmentComponent
                    .builder()
                    .view(fragment)
                    .baseComponent(baseComponent)
                    .build()
        }
    }

}
