package com.github.fatihsokmen.bookstore.products.data

import io.reactivex.Single
import javax.inject.Inject

class ProductApiInteractor @Inject constructor(
    private val apiService: ProductApiService,
    private val dtoToDomainMapper: ProductDtoToDomainMapper
) {

    fun getProducts(): Single<List<ProductDomain>> =
        apiService.getProducts().map { responseDto ->
            responseDto.products.map { dto ->
                dtoToDomainMapper.map(dto)
            }
        }

}

