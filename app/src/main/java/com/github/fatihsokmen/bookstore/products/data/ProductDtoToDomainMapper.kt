package com.github.fatihsokmen.bookstore.products.data

import com.github.fatihsokmen.bookstore.BuildConfig
import javax.inject.Inject

class ProductDtoToDomainMapper @Inject constructor() {

    fun map(dto: ProductDto): ProductDomain =
        ProductDomain(
            dto.id,
            dto.info.name,
            dto.info.description,
            price = Price(
                dto.price.currency,
                dto.price.total,
                dto.price.tax
            ),
            imageUrl = BuildConfig.MERCHANT_SERVER_URL + "/${dto.info.image}"
        )
}
