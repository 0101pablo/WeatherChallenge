package com.example.weatherchallenge.domain

data class CityModel(
    var name: String?,
    var country: String?,
    var lat: Float,
    var lon: Float,
    var currentLocation: Boolean = false
) {
    companion object {
        const val NAME = "name"
        const val COUNTRY = "country"
        const val LAT = "lat"
        const val LON = "lon"
        const val CURRENT_LOCATION = "currentLocation"
    }
}

