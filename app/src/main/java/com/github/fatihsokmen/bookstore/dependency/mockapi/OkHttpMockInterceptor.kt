package com.github.fatihsokmen.bookstore.dependency.mockapi

import com.github.fatihsokmen.bookstore.dependency.resource.AssetResources
import okhttp3.*

operator fun Regex.contains(text: CharSequence): Boolean = this.matches(text)

class OkHttpMockInterceptor(
        private val assetResources: AssetResources
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val path = chain.request().url().encodedPath()
        return when (path) {
            "/merchant_api/create_order" -> createOrder(chain)
            "/products_response.json" -> productResponseBuilder(chain)
            in Regex("([a-z\\-_0-9/:.]*\\.(jpg))") -> imageResponseBuilder(chain, path)
            else -> chain.proceed(chain.request())
        }
    }

    private fun productResponseBuilder(chain: Interceptor.Chain): Response {
        val json = assetResources.getAsset("products_response.json")
        return Response.Builder()
                .code(200)
                .message("OK")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                .addHeader("content-type", "application/json")
                .build()
    }

    private fun createOrder(chain: Interceptor.Chain): Response {
        val json = assetResources.getAsset("create_order_response.json")
        return Response.Builder()
            .code(200)
            .message("OK")
            .request(chain.request())
            .protocol(Protocol.HTTP_1_0)
            .body(ResponseBody.create(MediaType.parse("application/json"), json))
            .addHeader("content-type", "application/json")
            .build()
    }

    private fun imageResponseBuilder(chain: Interceptor.Chain, path: String): Response {
        val bytes = assetResources.getAssetAsByteBuffer("images$path")
        return Response.Builder()
                .code(200)
                .message("OK")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("/image/*"), bytes))
                .addHeader("content-type", "image/*")
                .build()
    }
}
