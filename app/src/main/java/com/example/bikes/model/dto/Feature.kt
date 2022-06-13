package com.example.bikes.model.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Feature {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("geometry")
    @Expose
    var geometry: Geometry? = null

    @SerializedName("properties")
    @Expose
    var properties: Properties? = null

}