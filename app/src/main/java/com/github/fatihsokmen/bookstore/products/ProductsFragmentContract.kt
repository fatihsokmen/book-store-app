package com.github.fatihsokmen.bookstore.products

import com.github.fatihsokmen.bookstore.products.data.ProductDomain


class ProductsFragmentContract {

    interface View {
        fun showProgress(show: Boolean)

        fun bindData(products: List<ProductDomain>)

        fun showError(message: String?)
    }

    interface Presenter {
        fun init()

        fun cleanup()
    }
}
