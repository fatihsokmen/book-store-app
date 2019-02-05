package com.github.fatihsokmen.bookstore.product_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.github.fatihsokmen.bookstore.R
import com.github.fatihsokmen.bookstore.products.data.ProductDomain

import kotlinx.android.synthetic.main.activity_product_details.*

class ProductDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_activity_product_details)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {

        @VisibleForTesting
        const val KEY_PRODUCT = "product"

        fun getIntent(context: Context, product: ProductDomain) =
                Intent(context, ProductDetailActivity::class.java).apply {
                    putExtra(KEY_PRODUCT, product)
                }
    }
}