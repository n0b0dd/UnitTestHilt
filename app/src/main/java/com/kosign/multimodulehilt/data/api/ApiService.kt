package com.kosign.multimodulehilt.data.api

import com.kosign.multimodulehilt.data.model.LocalWeather
import com.kosign.multimodulehilt.data.model.City
import com.kosign.multimodulehilt.data.model.Reservation
import com.kosign.multimodulehilt.util.RESERVATION_API
import com.kosign.multimodulehilt.util.WEATHER_API_CURRENT_WEATHER_URL
import com.kosign.multimodulehilt.util.WEATHER_API_SEARCH_URL
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(WEATHER_API_SEARCH_URL)
    fun queryCities(
        @Query("key") key: String,
        @Query("q") query: String
    ): Flow<List<City>>

    @GET(WEATHER_API_CURRENT_WEATHER_URL)
    fun currentWeather(
        @Query("key") key: String,
        @Query("q") query: String
    ): Flow<LocalWeather>

    @GET(RESERVATION_API)
    fun getReservationList(): Flow<List<Reservation>>

}