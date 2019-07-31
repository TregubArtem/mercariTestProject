package com.app.api

import com.app.a.whenDebug
import com.app.api.model.TimelineItemModel
import com.app.api.model.TimelineModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Interface that describe REST methods of server API
 */
interface IApi {
    /**
     *  @return list of timeline tabs (see [TimelineModel])
     */
    @GET(ApiPath.TABS)
    suspend fun getTabs(): List<TimelineModel>

    /**
     * @param source is uri to be called for retrieving the data
     * @return list of elements for timeline tab (see [TimelineItemModel])
     */
    @GET
    suspend fun getList(
        @Url source: String
                       ): List<TimelineItemModel>
}

private val apiImpl: IApi = run {
    val client = OkHttpClient.Builder()

    whenDebug {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = BODY
        client.addInterceptor(interceptor)
    }
    Retrofit.Builder()
        .baseUrl(ApiPath.BASE)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client.build())
        .build()
        .create(IApi::class.java)
}

object Api : IApi by apiImpl