package com.example.bikes.model.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class BikeStationModel(
    val id: String,
    val name: String,
    val availableBikes: Int,
    val availablePlaces: Int,
    val latitude: Double?,
    val longitude: Double?,
    var distance: Long
) : Parcelable