package com.github.fatihsokmen.bookstore.basket.data

data class CreatePaymentOrderResponseDto(
    val orderReference: String,
    val paymentUrl: String,
    val supportedCards: List<String>
)
