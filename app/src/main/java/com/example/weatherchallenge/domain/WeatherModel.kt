package com.example.weatherchallenge.domain

import com.google.gson.annotations.SerializedName

data class WeatherModel(
    var cityName: String,
    @SerializedName("weather")
    var weather: List<Weather>,
    @SerializedName("main")
    var main: Main,
    @SerializedName("wind")
    var wind: Wind
)

data class Weather(
    @SerializedName("description")
    var description: String
)

data class Main(
    @SerializedName("temp")
    var temp: Float,
    @SerializedName("feels_like")
    var feelsLike: Float,
    @SerializedName("temp_min")
    var tempMin: Float,
    @SerializedName("temp_max")
    var tempMax: Float,
    @SerializedName("pressure")
    var pressure: Float,
    @SerializedName("humidity")
    var humidity: Float
)

data class Wind(
    @SerializedName("speed")
    var speed: Float,
    @SerializedName("deg")
    var deg: Float
)