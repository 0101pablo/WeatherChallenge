package com.example.weatherchallenge.infra.services

import com.example.weatherchallenge.domain.WeatherModel
import com.example.weatherchallenge.tools.constants.ProviderConstants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrentWeatherService {

    @GET(ProviderConstants.PATH_CURRENT_WEATHER)
    fun getWeather(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("lang") lang: String = "pt",
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = ProviderConstants.WEATHER_API_APP_ID
    ): Call<WeatherModel>
}