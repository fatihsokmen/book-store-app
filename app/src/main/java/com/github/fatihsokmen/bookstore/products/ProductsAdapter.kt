package com.github.fatihsokmen.bookstore.products


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.fatihsokmen.bookstore.R
import com.github.fatihsokmen.bookstore.dependency.formatter.Formatter
import com.github.fatihsokmen.bookstore.products.data.ProductDomain
import com.github.fatihsokmen.bookstore.products.viewholder.ProductsViewHolder
import javax.inject.Inject


class ProductsAdapter @Inject constructor(
    private val formatter: Formatter
) : RecyclerView.Adapter<ProductsViewHolder>() {

    private var data = emptyList<ProductDomain>()

    fun setData(data: List<ProductDomain>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_product_item, parent, false)
        return ProductsViewHolder(view, formatter)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
