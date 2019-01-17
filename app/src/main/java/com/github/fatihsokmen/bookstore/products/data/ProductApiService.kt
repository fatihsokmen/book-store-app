package com.github.fatihsokmen.bookstore.products.data

import io.reactivex.Single
import retrofit2.http.GET

interface ProductApiService {

    @GET(value = "products_response.json")
    fun getProducts(): Single<ProductsResponseDto>
}
