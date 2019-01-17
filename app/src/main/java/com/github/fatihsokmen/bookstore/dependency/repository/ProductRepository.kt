package com.github.fatihsokmen.bookstore.dependency.repository

import com.github.fatihsokmen.bookstore.basket.data.BasketProductDomain
import com.github.fatihsokmen.bookstore.products.data.ProductDomain
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject


class ProductRepository @Inject constructor(
        private val productDao: ProductDao,
        private val entityToDomainMapper: EntityToDomainMapper
) {

    fun getBasketProducts(): Flowable<List<BasketProductDomain>> =
            productDao.getProducts().map { entities ->
                entities.map { entity ->
                    entityToDomainMapper.map(entity)
                }
            }

    fun insertProduct(product: ProductDomain): Completable =
            Completable.fromAction {
                productDao.insert(
                        ProductEntity(
                                id = product.id,
                                name = product.name,
                                description = product.description,
                                imageUrl = product.imageUrl,
                                price = product.price,
                                amount = 1
                        )
                )
            }

    fun deleteProduct(id: String): Completable {
        return Completable.fromCallable {
            productDao.delete(id)
        }
    }

    fun hasProduct(id: String): Single<Boolean> {
        return productDao.findProductBy(id).flatMap { entities ->
            return@flatMap Single.just(entities.isNotEmpty())
        }
    }

    fun removeAll(): Completable {
        return Completable.fromCallable {
            productDao.deleteAll()
        }
    }
}