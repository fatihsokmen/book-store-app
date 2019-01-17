package com.github.fatihsokmen.bookstore.basket


import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.github.fatihsokmen.bookstore.basket.data.BasketProductDomain
import com.github.fatihsokmen.bookstore.basket.viewholder.BasketProductViewHolderFactory
import com.github.fatihsokmen.bookstore.basket.viewholder.BasketProductViewHolderView


class BasketProductsAdapter(
    private val viewHolderFactoryBuilder: BasketProductViewHolderFactory.Builder,
    private val interactions: BasketFragmentContract.Interactions
) : RecyclerView.Adapter<BasketProductViewHolderView>() {

    private var data = emptyList<BasketProductDomain>()

    fun setData(data: List<BasketProductDomain>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketProductViewHolderView {
        return viewHolderFactoryBuilder.parentView(parent)
            .interactions(interactions)
            .build()
            .createViewHolder()
    }

    override fun onBindViewHolder(holder: BasketProductViewHolderView, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
