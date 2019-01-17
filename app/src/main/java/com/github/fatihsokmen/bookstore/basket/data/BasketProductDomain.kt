package com.github.fatihsokmen.bookstore.basket.data

import com.github.fatihsokmen.bookstore.products.data.Price


data class BasketProductDomain(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: Price,
    val amount: Int
)
