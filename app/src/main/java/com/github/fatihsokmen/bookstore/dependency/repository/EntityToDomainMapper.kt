package com.github.fatihsokmen.bookstore.dependency.repository

import com.github.fatihsokmen.bookstore.basket.data.BasketProductDomain
import javax.inject.Inject

class EntityToDomainMapper @Inject constructor() {

    fun map(entity: ProductEntity): BasketProductDomain =
            BasketProductDomain(
                    entity.id,
                    entity.name,
                    entity.description,
                    entity.imageUrl,
                    entity.price,
                    entity.amount
            )

}