package com.github.fatihsokmen.bookstore.dependency.resource

interface AssetResources {

    fun getAsset(file: String): String

    fun getAssetAsByteBuffer(file: String): ByteArray
}
