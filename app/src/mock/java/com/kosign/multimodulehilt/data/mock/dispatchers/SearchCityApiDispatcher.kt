package com.kosign.multimodulehilt.data.mock.dispatchers

import com.kosign.multimodulehilt.data.mock.MockUtils
import com.kosign.multimodulehilt.data.mock.mockserver.MockScenarios
import com.kosign.multimodulehilt.util.WEATHER_API_SEARCH_URL
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class SearchCityApiDispatcher(
    private val mockApis: Map<String, MockScenarios>
) : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        return when(mockApis[WEATHER_API_SEARCH_URL]) {
            MockScenarios.SUCCESS ->
                MockUtils.success("search_api_success.json")
            MockScenarios.ERROR ->
                MockUtils.success("search_api_error.json")
            MockScenarios.FAILURE ->
                MockUtils.failure(500, "search_api_error.json")
            else ->
                MockUtils.default
        }
    }
}