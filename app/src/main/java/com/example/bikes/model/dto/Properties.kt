package com.example.bikes.model.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Properties {
    @SerializedName("free_racks")
    @Expose
    var freeRacks: String? = null

    @SerializedName("bikes")
    @Expose
    var bikes: String? = null

    @SerializedName("label")
    @Expose
    var label: String? = null

    @SerializedName("bike_racks")
    @Expose
    var bikeRacks: String? = null

    @SerializedName("updated")
    @Expose
    var updated: String? = null

}