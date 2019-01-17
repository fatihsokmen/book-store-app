package com.github.fatihsokmen.bookstore.products.data

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.util.*

data class ProductsResponseDto(
    @SerializedName("products") val products: List<ProductDto>
)

data class ProductDto(
    @SerializedName("id") val id: String,
    @SerializedName("info") val info: ProductInfoDto,
    @SerializedName("price") val price: PriceDto
)

data class ProductInfoDto(
    @SerializedName("locale") val locale: String,
    @SerializedName("productDescription") val description: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String
)

data class PriceDto(
    @SerializedName("total") val total: BigDecimal,
    @SerializedName("tax") val tax: BigDecimal,
    @SerializedName("currency") val currency: Currency
)