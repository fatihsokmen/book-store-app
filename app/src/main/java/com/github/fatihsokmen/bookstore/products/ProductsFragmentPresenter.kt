package com.github.fatihsokmen.bookstore.products

import com.github.fatihsokmen.bookstore.dependency.scheduler.Scheduler
import com.github.fatihsokmen.bookstore.products.data.ProductApiInteractor
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ProductsFragmentPresenter @Inject constructor(
    private val view: ProductsFragmentContract.View,
    private val productApInteractor: ProductApiInteractor,
    private val scheduler: Scheduler
) : ProductsFragmentContract.Presenter {

    private val subscriptions = CompositeDisposable()

    override fun init() {
        view.showProgress(true)
        subscriptions.addAll(
            productApInteractor.getProducts()
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.main())
                .subscribe({ products ->
                    view.showProgress(false)
                    view.bindData(products)

                }, { error ->
                    view.showProgress(false)
                    view.showError(error.message)
                })
        )
    }

    override fun cleanup() {
        subscriptions.dispose()
    }
}
