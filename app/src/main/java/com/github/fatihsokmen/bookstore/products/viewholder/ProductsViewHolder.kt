package com.github.fatihsokmen.bookstore.products.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.github.fatihsokmen.bookstore.dependency.formatter.Formatter
import com.github.fatihsokmen.bookstore.product_detail.ProductDetailActivity
import com.github.fatihsokmen.bookstore.products.data.ProductDomain
import kotlinx.android.extensions.LayoutContainer
import java.util.*
import javax.inject.Inject

import kotlinx.android.synthetic.main.view_product_item.*

class ProductsViewHolder @Inject constructor(
    override val containerView: View,
    private val formatter: Formatter
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        productLayout.setOnClickListener {
            onProductClicked()
        }
    }

    private lateinit var product: ProductDomain

    fun bind(product: ProductDomain) {
        this.product = product
        productNameView.text = product.name
        productPriceView.text = formatter.formatAmount(product.price.currency, product.price.total, Locale.UK)
        Glide.with(itemView.context).load(product.imageUrl).into(productImageView)
    }

    private fun onProductClicked() {
        itemView.context.apply {
            startActivity(ProductDetailActivity.getIntent(this, product))
        }
    }
}