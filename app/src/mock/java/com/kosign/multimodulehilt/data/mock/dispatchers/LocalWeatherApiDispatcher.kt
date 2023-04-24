package com.kosign.multimodulehilt.data.mock.dispatchers

import com.kosign.multimodulehilt.data.mock.MockUtils
import com.kosign.multimodulehilt.data.mock.mockserver.MockScenarios
import com.kosign.multimodulehilt.util.WEATHER_API_CURRENT_WEATHER_URL
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class LocalWeatherApiDispatcher(
    private val mockApis: Map<String, MockScenarios>
) : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        return when(mockApis[WEATHER_API_CURRENT_WEATHER_URL]) {
            MockScenarios.SUCCESS ->
                MockUtils.success("weather_api_success.json")
            MockScenarios.FAILURE ->
                MockUtils.failure(500, "weather_api_success.json")
            else ->
                MockUtils.default
        }
    }
}