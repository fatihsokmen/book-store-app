package com.github.fatihsokmen.bookstore.products.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.github.fatihsokmen.bookstore.R
import com.github.fatihsokmen.bookstore.dependency.formatter.Formatter
import com.github.fatihsokmen.bookstore.product_detail.ProductDetailActivity
import com.github.fatihsokmen.bookstore.products.data.ProductDomain
import java.util.*
import javax.inject.Inject

class ProductsViewHolder @Inject constructor(
    itemView: View,
    private val formatter: Formatter
) : RecyclerView.ViewHolder(itemView) {

    @BindView(R.id.basket_product_image)
    lateinit var productImageView: ImageView
    @BindView(R.id.basket_product_name)
    lateinit var productNameView: TextView
    @BindView(R.id.product_price)
    lateinit var productPriceView: TextView

    private lateinit var product: ProductDomain

    init {
        ButterKnife.bind(this, itemView)
    }

    fun bind(product: ProductDomain) {
        this.product = product

        productNameView.text = product.name
        productPriceView.text = formatter.formatAmount(product.price.currency, product.price.total, Locale.UK)
        Glide.with(itemView.context).load(product.imageUrl).into(productImageView)
    }

    @OnClick(R.id.product)
    fun onProductClicked() {
        itemView.context.apply {
            startActivity(ProductDetailActivity.getIntent(this, product))
        }
    }
}