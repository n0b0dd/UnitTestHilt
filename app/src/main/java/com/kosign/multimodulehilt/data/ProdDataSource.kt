package com.kosign.multimodulehilt.data

import com.kosign.multimodulehilt.data.model.City
import com.kosign.multimodulehilt.data.model.CurrentCondition
import com.kosign.multimodulehilt.data.model.Reservation
import kotlinx.coroutines.flow.Flow

interface ProdDataSource {

    suspend fun queryCities(query: String): Flow<List<City>>

    suspend fun currentWeather(query: String): Flow<CurrentCondition?>

    suspend fun getReservation(): Flow<List<Reservation>?>

}