package com.github.fatihsokmen.bookstore.basket.data

import io.reactivex.Single
import java.math.BigDecimal
import javax.inject.Inject

class PaymentOrderApiInteractor @Inject constructor(
    private val merchantApiService: MerchantApiService
) {

    fun createPaymentOrder(amount: BigDecimal, currency: String): Single<CreatePaymentOrderResponseDomain> {
        val request = CreatePaymentOrderRequestDto(
            amount = PaymentOrderAmountDto(amount.toInt(), currency)
        )
        return merchantApiService.createPaymentOrder(request).map { dto ->
            CreatePaymentOrderResponseDomain(
                orderReference = dto.orderReference,
                paymentUrl = dto.paymentUrl
            )
        }
    }
}

