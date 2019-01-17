package com.github.fatihsokmen.bookstore.basket.data

import io.reactivex.Single
import retrofit2.http.*

interface MerchantApiService {

    @POST(value = "merchant_api/create_order")
    fun createPaymentOrder(@Body createPaymentOrderRequest: CreatePaymentOrderRequestDto): Single<CreatePaymentOrderResponseDto>


}
