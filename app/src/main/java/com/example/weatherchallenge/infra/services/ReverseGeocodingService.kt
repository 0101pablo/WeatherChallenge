package com.example.weatherchallenge.infra.services

import com.example.weatherchallenge.domain.ReverseGeoCodeModel
import com.example.weatherchallenge.tools.constants.ProviderConstants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ReverseGeocodingService {
    @GET(ProviderConstants.PATH_URL_REVERSE_GEOCODING)
    fun getReverseGeoCode(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("limit") limit: Int = 1,
        @Query("appid") appid: String = ProviderConstants.WEATHER_API_APP_ID
    ): Call<List<ReverseGeoCodeModel>>
}