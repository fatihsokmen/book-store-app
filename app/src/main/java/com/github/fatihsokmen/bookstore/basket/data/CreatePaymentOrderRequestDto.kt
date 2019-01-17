package com.github.fatihsokmen.bookstore.basket.data

data class CreatePaymentOrderRequestDto(
    private val amount: PaymentOrderAmountDto,
    private val description: String = "Book Store Android App"
)

data class PaymentOrderAmountDto(
    private val value: Int,
    private val currencyCode: String
)
