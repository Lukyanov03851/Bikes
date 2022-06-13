package com.example.bikes.network

import com.example.bikes.model.dto.BikeStationsListResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BikeStationsApiService {
    @GET(Endpoints.BIKE_STATIONS)
    fun loadBikeStationsAsync(
        @Query("mtype") type: String = "pub_transport",
        @Query("co") co: String = "stacje_rowerowe",
    ): Deferred<Response<BikeStationsListResponse>>
}