package com.github.fatihsokmen.bookstore.dependency.repository

import android.arch.persistence.room.TypeConverter
import com.github.fatihsokmen.bookstore.products.data.Price
import com.google.gson.GsonBuilder

object PriceTypeConverter {

    private val gson = GsonBuilder().create()

    @TypeConverter
    @JvmStatic
    fun toString(values: Price): String {
        return gson.toJson(values)
    }

    @TypeConverter
    @JvmStatic
    fun toPrice(json: String): Price {
        return gson.fromJson(json, Price::class.java)
    }
}