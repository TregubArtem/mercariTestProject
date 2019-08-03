package com.app.api.mock

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

/** Interceptor that doing nothing. Created only because of the "mock" flavor has it in use. */
class MockApiInterceptor : Interceptor {
    override fun intercept(chain: Chain): Response =
        chain.proceed(chain.request())
}