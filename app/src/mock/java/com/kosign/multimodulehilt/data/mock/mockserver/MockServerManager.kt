package com.kosign.multimodulehilt.data.mock.mockserver

import android.util.Log
import com.kosign.multimodulehilt.data.mock.dispatchers.ApiDispatcher
import com.kosign.multimodulehilt.util.ENABLE_SSL_PINNING
import com.kosign.multimodulehilt.util.LocalHostSocketFactory
import okhttp3.mockwebserver.MockWebServer

class MockServerManager {
    var port = 1
    var scheme: String? = null
        get() {
            return when {
                field != null ->
                    field
                else ->
                    HTTP_SCHEME
            }
        }
    private val mockApis = mutableMapOf<String, MockScenarios>()
    private var mockServer: MockWebServer? = null
    private var isServerStarted = false

    fun startServer() {
        if (!isServerStarted){
            Thread {
                mockServer = MockWebServer()
                mockServer!!.dispatcher = ApiDispatcher(mockApis)
                mockServer!!.start()
                port = mockServer!!.port
                Log.d(TAG, "Started mock server at: ${mockServer!!.url("")} :: port ${port}")
                isServerStarted = true
            }.start()
        }

    }

    fun stopServer() {
        isServerStarted = false
        mockServer?.shutdown()
    }

    fun enableApi(api: String, scenarios: MockScenarios) {
        mockApis[api] = scenarios
    }

    fun disableApi(api: String) {
        if (mockApis.contains(api)) {
            mockApis.remove(api)
        }
    }

    fun shouldMockApi(api: String): Boolean {
        mockApis.forEach {
            if (api.contains(it.key)) {
                return true
            }
        }
        return false
    }

    companion object {
        private const val TAG = "MockServerManager"
        const val HTTP_SCHEME = "http"
//        const val HTTPS_SCHEME = "https"
        const val HOST = "localhost"
    }
}