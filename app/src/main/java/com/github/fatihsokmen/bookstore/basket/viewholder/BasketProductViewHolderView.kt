package com.github.fatihsokmen.bookstore.basket.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.github.fatihsokmen.bookstore.basket.BasketFragmentContract
import com.github.fatihsokmen.bookstore.basket.data.BasketProductDomain
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_basket_product_item.*
import javax.inject.Inject

class BasketProductViewHolderView @Inject constructor(
    override val containerView: View,
    private val interactions: BasketFragmentContract.Interactions
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        deleteButton.setOnClickListener {
            interactions.onDeleteClicked(basketProduct.id)
        }
    }

    private lateinit var basketProduct: BasketProductDomain

    fun bind(basketProduct: BasketProductDomain) {
        this.basketProduct = basketProduct
        basketProductName.text = basketProduct.name
        Glide.with(itemView.context).load(basketProduct.imageUrl).into(basketProductImage)
    }

}