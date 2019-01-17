package com.github.fatihsokmen.bookstore.product_detail

import com.github.fatihsokmen.bookstore.products.data.ProductDomain


class ProductDetailsFragmentContract {

    interface View {
        fun bindData(product: ProductDomain, productInBasket: Boolean)

        fun dismiss()
    }

    interface Presenter {
        fun init(product: ProductDomain)

        fun cleanup()

        fun addToBasket()
    }
}
