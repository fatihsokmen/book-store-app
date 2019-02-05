package com.github.fatihsokmen.bookstore.products.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal
import java.util.*

@Parcelize
data class ProductDomain constructor(
    val id: String,
    val name: String,
    val description: String,
    val price: Price,
    val imageUrl: String
) : Parcelable

@Parcelize
class Price constructor(
    val currency: Currency,
    val total: BigDecimal,
    val tax: BigDecimal
) : Parcelable


