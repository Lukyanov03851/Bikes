package com.example.bikes.model.mappers

import com.example.bikes.model.domain.BikeStationModel
import com.example.bikes.model.dto.Feature

class BikeStationMapper : Mapper<Feature, BikeStationModel> {
    override fun transform(src: Feature): BikeStationModel {
        return BikeStationModel(
            id = src.id ?: "",
            name = src.properties?.label ?: "",
            availableBikes = src.properties?.bikes?.toIntOrNull() ?: 0,
            availablePlaces =  src.properties?.freeRacks?.toIntOrNull() ?: 0,
            longitude = src.geometry?.coordinates?.firstOrNull(),
            latitude = src.geometry?.coordinates?.getOrNull(1),
            distance = 0
        )
    }
}