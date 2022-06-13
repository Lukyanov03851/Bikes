package com.example.bikes.data

import com.example.bikes.model.domain.BikeStationModel
import com.example.bikes.model.mappers.BikeStationMapper
import com.example.bikes.network.Resource
import com.example.bikes.network.BikeStationsApiService
import com.example.bikes.utils.ErrorHelper
import javax.inject.Inject

interface BikeStationsRepo {
    suspend fun getBikeStationsList(): Resource<List<BikeStationModel>>
}

class BikeStationsRepoImpl @Inject constructor(
    private val apiService: BikeStationsApiService
) : BikeStationsRepo {


    override suspend fun getBikeStationsList(): Resource<List<BikeStationModel>> {
        return try {
            val response = apiService.loadBikeStationsAsync().await()
            if (response.isSuccessful) {
                val mapper = BikeStationMapper()
                val sourcesList =
                    response.body()?.features?.map { mapper.transform(it) } ?: listOf()
                Resource.success(sourcesList)
            } else {
                Resource.error(ErrorHelper.getErrorMessage(response.errorBody()))
            }
        } catch (e: Exception) {
            ErrorHelper.processException(e)
        }

    }
}