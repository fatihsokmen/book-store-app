package com.github.fatihsokmen.bookstore.basket

import javax.inject.Inject

class BasketProductViewHolderInteractions @Inject constructor(
    private val basketPresenter: BasketFragmentContract.Presenter
) : BasketFragmentContract.Interactions {

    override fun onDeleteClicked(id: String) {
        basketPresenter.deleteProduct(id)
    }
}
