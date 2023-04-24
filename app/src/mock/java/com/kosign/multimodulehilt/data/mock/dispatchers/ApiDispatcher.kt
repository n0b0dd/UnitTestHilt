package com.kosign.multimodulehilt.data.mock.dispatchers

import com.kosign.multimodulehilt.data.mock.mockserver.MockScenarios
import com.kosign.multimodulehilt.util.RESERVATION_API
import com.kosign.multimodulehilt.util.WEATHER_API_CURRENT_WEATHER_URL
import com.kosign.multimodulehilt.util.WEATHER_API_SEARCH_URL
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class ApiDispatcher(
    private val mockApis: Map<String, MockScenarios>
) : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        return when {
            request.path?.startsWith(WEATHER_API_SEARCH_URL) == true ->
                SearchCityApiDispatcher(mockApis).dispatch(request)
            request.path?.startsWith(WEATHER_API_CURRENT_WEATHER_URL) == true ->
                LocalWeatherApiDispatcher(mockApis).dispatch(request)
            request.path?.startsWith(RESERVATION_API) == true ->
                ReservationApiDispatcher(mockApis).dispatch(request)
            else ->
                MockResponse().setResponseCode(404)
        }
    }
}