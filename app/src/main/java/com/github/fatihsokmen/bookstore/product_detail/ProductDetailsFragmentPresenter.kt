package com.github.fatihsokmen.bookstore.product_detail

import com.github.fatihsokmen.bookstore.dependency.repository.ProductRepository
import com.github.fatihsokmen.bookstore.dependency.scheduler.Scheduler
import com.github.fatihsokmen.bookstore.products.data.ProductDomain
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ProductDetailsFragmentPresenter @Inject constructor(
        private val view: ProductDetailsFragmentContract.View,
        private val repository: ProductRepository,
        private val scheduler: Scheduler
) : ProductDetailsFragmentContract.Presenter {

    private val subscriptions = CompositeDisposable()

    private lateinit var product: ProductDomain

    override fun init(product: ProductDomain) {
        this.product = product

        subscriptions.add(repository.hasProduct(product.id)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.main())
                .subscribe({ productInBasket ->
                    view.bindData(product, productInBasket)
                }, {
                    view.dismiss()
                }))
    }

    override fun addToBasket() {
        subscriptions.add(repository.insertProduct(product)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.main())
                .subscribe {
                    view.dismiss()
                })
    }

    override fun cleanup() {
        subscriptions.dispose()
    }
}
