package com.github.fatihsokmen.bookstore.basket.viewholder

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.github.fatihsokmen.bookstore.R
import com.github.fatihsokmen.bookstore.basket.BasketFragmentContract
import com.github.fatihsokmen.bookstore.basket.data.BasketProductDomain
import javax.inject.Inject

class BasketProductViewHolderView @Inject constructor(
    itemView: View,
    private val interactions: BasketFragmentContract.Interactions
) : RecyclerView.ViewHolder(itemView) {

    @BindView(R.id.basket_product_image)
    lateinit var basketProductImage: ImageView
    @BindView(R.id.basket_product_name)
    lateinit var basketProductName: TextView

    private lateinit var basketProduct: BasketProductDomain

    init {
        ButterKnife.bind(this, itemView)
    }

    @SuppressLint("SetTextI18n")
    fun bind(basketProduct: BasketProductDomain) {
        this.basketProduct = basketProduct
        basketProductName.text = basketProduct.name
        Glide.with(itemView.context).load(basketProduct.imageUrl).into(basketProductImage)
    }

    @OnClick(R.id.delete_button)
    fun onDeleteClicked() {
        interactions.onDeleteClicked(basketProduct.id)
    }

}