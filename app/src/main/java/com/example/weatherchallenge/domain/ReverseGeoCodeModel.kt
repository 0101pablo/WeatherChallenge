package com.example.weatherchallenge.domain

import com.google.gson.annotations.SerializedName

data class ReverseGeoCodeModel(
    @SerializedName("name")
    val cityName: String,
    @SerializedName("local_names")
    var localNames: LocalNames,
    @SerializedName("lat")
    var lat: Float,
    @SerializedName("lon")
    var lon: Float
)

data class LocalNames(
    @SerializedName("pt")
    var cityNamePT: String
)