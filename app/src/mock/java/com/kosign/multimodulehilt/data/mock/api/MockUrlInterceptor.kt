package com.kosign.multimodulehilt.data.mock.api

import android.util.Log
import com.kosign.multimodulehilt.data.mock.mockserver.MockServerManager
import com.kosign.multimodulehilt.data.mock.mockserver.MockServerManager.Companion.HTTP_SCHEME
import com.kosign.multimodulehilt.util.CertificatePinnerHostVerifier
import com.kosign.multimodulehilt.util.ENABLE_SSL_PINNING
import okhttp3.*
import java.io.IOException
import javax.inject.Inject

class MockUrlInterceptor :Interceptor {

    var mockServerManager: MockServerManager? = null

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        Log.d(">>>", "intercept: ${mockServerManager?.shouldMockApi(request.url.encodedPath)}")
        if (mockServerManager?.shouldMockApi(request.url.encodedPath) == true) {
            // Only validate the host name when you mock the API
            val newUrl: HttpUrl = request.url.newBuilder()
                .scheme(mockServerManager?.scheme ?: HTTP_SCHEME)
                .host(MockServerManager.HOST)
                .port(mockServerManager?.port ?: 1)
                .build()
            request = request.newBuilder()
                .url(newUrl)
                .build()
            Log.d(">>>", "intercept: ${newUrl.toString()}")
        }
        return chain.proceed(request)
    }
}