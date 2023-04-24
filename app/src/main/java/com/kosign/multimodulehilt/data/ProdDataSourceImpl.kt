package com.kosign.multimodulehilt.data

import android.util.Log
import com.kosign.multimodulehilt.data.api.ApiService
import com.kosign.multimodulehilt.data.model.City
import com.kosign.multimodulehilt.data.model.CurrentCondition
import com.kosign.multimodulehilt.data.model.Reservation
import com.kosign.multimodulehilt.util.WEATHER_API_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Retrofit
import javax.inject.Inject

class ProdDataSourceImpl constructor(retrofit: Retrofit) : ProdDataSource {

    private val apiService = retrofit.create(ApiService::class.java)

    override suspend fun queryCities(query: String): Flow<List<City>> = apiService.queryCities(WEATHER_API_KEY, query)

    override suspend fun currentWeather(query: String): Flow<CurrentCondition?> = apiService.currentWeather(WEATHER_API_KEY, query).map { it.current }

    override suspend fun getReservation(): Flow<List<Reservation>> = apiService.getReservationList()
}