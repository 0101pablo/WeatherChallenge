package com.example.weatherchallenge.infra.repositories

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.content.ContextCompat
import com.example.weatherchallenge.domain.CityModel
import com.example.weatherchallenge.domain.ReverseGeoCodeModel
import com.example.weatherchallenge.domain.WeatherModel
import com.example.weatherchallenge.infra.provider.NetworkUtils
import com.example.weatherchallenge.infra.services.CurrentWeatherService
import com.example.weatherchallenge.infra.services.ReverseGeocodingService
import com.example.weatherchallenge.tools.constants.ProviderConstants
import com.google.android.gms.location.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class WeatherRepository {

    suspend fun getWeather(cityBundle: CityModel): WeatherModel? = suspendCoroutine { cont ->
        val retrofitClient =
            NetworkUtils.getRetrofitInstance(ProviderConstants.BASE_URL_CURRENT_WEATHER)

        val endpoint = retrofitClient.create(CurrentWeatherService::class.java)
        val callback = endpoint.getWeather(cityBundle.lat, cityBundle.lon)

        callback.enqueue(object : Callback<WeatherModel> {
            override fun onResponse(
                call: Call<WeatherModel>,
                response: Response<WeatherModel>
            ) {
                cont.resume(response.body())
            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                cont.resume(null)
            }
        })
    }

    suspend fun getReverseGeoCode(lat: Float, lon: Float): List<ReverseGeoCodeModel>? =
        suspendCoroutine { cont ->
            val retrofitClient =
                NetworkUtils.getRetrofitInstance(ProviderConstants.BASE_URL_GEOCODING)

            val endpoint = retrofitClient.create(ReverseGeocodingService::class.java)
            val callback = endpoint.getReverseGeoCode(lat, lon)

            callback.enqueue(object : Callback<List<ReverseGeoCodeModel>> {
                override fun onResponse(
                    call: Call<List<ReverseGeoCodeModel>>,
                    response: Response<List<ReverseGeoCodeModel>>
                ) {
                    cont.resume(response.body())
                }

                override fun onFailure(call: Call<List<ReverseGeoCodeModel>>, t: Throwable) {
                    cont.resume(null)
                }
            })
        }

//    suspend fun getLastKnownLocation(context: Context): ArrayList<Float> =
//        suspendCoroutine { cont ->
//            val fusedLocationClient: FusedLocationProviderClient =
//                LocationServices.getFusedLocationProviderClient(context)
//            if (ContextCompat.checkSelfPermission(
//                    context, Manifest.permission.ACCESS_COARSE_LOCATION
//                ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
//                    context, Manifest.permission.ACCESS_FINE_LOCATION
//                ) == PackageManager.PERMISSION_GRANTED
//            ) {
//                val locationRequest: LocationRequest = LocationRequest.create()
//                locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//                locationRequest.interval = 500
//                locationRequest.fastestInterval = 250
//                fusedLocationClient.requestLocationUpdates(
//                    locationRequest, object : LocationCallback() {
//                        override fun onLocationResult(requestLocation: LocationResult) {
//                            requestLocation.lastLocation.let { location ->
//                                val lat = location.latitude.toFloat()
//                                val lon = location.longitude.toFloat()
//                                val locationArray = arrayListOf(lat, lon)
//                                cont.resume(locationArray)
//                            }
//                        }
//                    }, Looper.getMainLooper()
//                )
//            }
//        }
}