package com.github.fatihsokmen.bookstore.products


import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.fatihsokmen.bookstore.App
import com.github.fatihsokmen.bookstore.R
import com.github.fatihsokmen.bookstore.products.data.ProductDomain
import javax.inject.Inject

import kotlinx.android.synthetic.main.fragment_products.*

class ProductsFragment : Fragment(), ProductsFragmentContract.View {

    @Inject
    internal lateinit var adapter: ProductsAdapter

    @Inject
    internal lateinit var presenter: ProductsFragmentContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createProductsComponent(this).inject(this)

        products.adapter = adapter
        presenter.init()
    }

    override fun bindData(products: List<ProductDomain>) {
        adapter.setData(products)
    }

    override fun showProgress(show: Boolean) {
        progressView.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showError(message: String?) {
        view?.let {
            Snackbar.make(it, message
                    ?: getString(R.string.unknown_error), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        presenter.cleanup()
        super.onDestroyView()
    }

    companion object {
        private fun createProductsComponent(fragment: ProductsFragment)
                : ProductsFragmentComponent {
            val baseComponent = (fragment.activity?.application as App).baseComponent
            return DaggerProductsFragmentComponent
                    .builder()
                    .view(fragment)
                    .baseComponent(baseComponent)
                    .build()
        }
    }

}
