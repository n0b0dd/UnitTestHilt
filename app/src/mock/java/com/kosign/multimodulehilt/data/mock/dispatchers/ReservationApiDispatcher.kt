package com.kosign.multimodulehilt.data.mock.dispatchers

import com.kosign.multimodulehilt.data.mock.MockUtils
import com.kosign.multimodulehilt.data.mock.mockserver.MockScenarios
import com.kosign.multimodulehilt.util.RESERVATION_API
import okhttp3.mockwebserver.Dispatcher

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class ReservationApiDispatcher (private val mockApis: Map<String, MockScenarios>) : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when(mockApis[RESERVATION_API]) {
            MockScenarios.SUCCESS ->
                MockUtils.success("reservation_api_success.json")
            MockScenarios.FAILURE ->
                MockUtils.failure(500, "weather_api_success.json")
            else ->
                MockUtils.default
        }
    }

}