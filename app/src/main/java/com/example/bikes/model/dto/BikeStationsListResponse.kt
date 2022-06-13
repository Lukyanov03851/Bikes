package com.example.bikes.model.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BikeStationsListResponse {
    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("crs")
    @Expose
    var crs: Any? = null

    @SerializedName("features")
    @Expose
    var features: List<Feature>? = null

}