package com.github.fatihsokmen.bookstore.dependency.repository

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.github.fatihsokmen.bookstore.products.data.Price
import java.util.Calendar
import java.util.Date

@Entity(tableName = "Product")
data class ProductEntity(
        @PrimaryKey
        val id: String,
        @ColumnInfo(name = "name")
        val name: String,
        @ColumnInfo(name = "description")
        val description: String,
        @ColumnInfo(name = "imageUrl")
        val imageUrl: String,
        @ColumnInfo(name = "total")
        val price: Price,
        @ColumnInfo(name = "amount")
        val amount: Int,
        @ColumnInfo(name = "date")
        val date: Date = Calendar.getInstance().time
)

