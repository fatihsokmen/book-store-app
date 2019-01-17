package com.github.fatihsokmen.bookstore.basket.data

data class CreatePaymentOrderResponseDomain(
    val orderReference: String,
    val paymentUrl: String
)
