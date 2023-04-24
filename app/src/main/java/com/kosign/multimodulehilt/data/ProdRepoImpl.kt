package com.kosign.multimodulehilt.data

import com.kosign.multimodulehilt.data.model.City
import com.kosign.multimodulehilt.data.model.CurrentCondition
import com.kosign.multimodulehilt.data.model.Reservation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProdRepoImpl @Inject constructor(private val dataSource: ProdDataSource) : ProdRepository {

    override suspend fun queryCities(query: String): Flow<List<City>> = dataSource.queryCities(query)

    override suspend fun currentWeather(query: String): Flow<CurrentCondition?> = dataSource.currentWeather(query)

    override suspend fun getReservation(): Flow<List<Reservation>?> = dataSource.getReservation()
}