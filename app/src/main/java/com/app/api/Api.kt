package com.app.api

import com.app.api.mock.MockApiInterceptor
import com.app.api.model.CategoryModel
import com.app.api.model.TimelineItemModel
import com.app.global.whenDebug
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

/** Interface that describe REST methods of server API */
interface IApi {
    /**
     *  @return list of timeline tabs (see [CategoryModel])
     */
    @GET(ApiPath.TABS)
    suspend fun getTabs(): List<CategoryModel>

    /**
     * @param source is uri to be called for retrieving the data
     * @return list of elements for timeline tab (see [TimelineItemModel])
     */
    @GET
    suspend fun getList(
        @Url source: String
                       ): List<TimelineItemModel>
}

/** Implementation of API interface. Used like layer for singleton [Api] */
private val apiImpl: IApi = run {
    val client = OkHttpClient.Builder()

    whenDebug {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = BODY
        client.addInterceptor(interceptor)
    }
    client.addInterceptor(MockApiInterceptor())

    Retrofit.Builder()
        .baseUrl(ApiPath.BASE)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client.build())
        .build()
        .create(IApi::class.java)
}

/** Singleton for single place access to API methods */
object Api : IApi by apiImpl