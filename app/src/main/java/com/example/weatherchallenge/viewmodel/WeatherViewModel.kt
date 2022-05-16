package com.example.weatherchallenge.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherchallenge.domain.CityModel
import com.example.weatherchallenge.domain.ReverseGeoCodeModel
import com.example.weatherchallenge.domain.WeatherModel
import com.example.weatherchallenge.infra.repositories.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    val weatherLiveData = MutableLiveData<WeatherModel?>()

    fun getWeather(cityBundle: CityModel) {
        CoroutineScope(Dispatchers.Main).launch {
            val weather = withContext(Dispatchers.Default) {
                repository.getWeather(cityBundle)
            }
            weather?.cityName = cityBundle.name ?: "Falha ao carregar o nome"
            weatherLiveData.value = weather
        }
    }

    val reverseGeoCodeLiveData = MutableLiveData<List<ReverseGeoCodeModel?>?>()

    fun getReverseGeoCode(lat: Float, lon: Float) {
        CoroutineScope(Dispatchers.Main).launch {
            val geoCode = withContext(Dispatchers.Default) {
                repository.getReverseGeoCode(lat, lon)
            }
            reverseGeoCodeLiveData.value = geoCode
        }
    }

//    val locationsLiveData = MutableLiveData<ArrayList<Float>>()
//
//    fun getLastKnownLocation(context: Context) {
//        CoroutineScope(Dispatchers.Main).launch {
//            val locations = withContext(Dispatchers.Default) {
//                repository.getLastKnownLocation(context)
//            }
//            locationsLiveData.value = locations
//        }
//    }

    class WeatherViewModelFactory(
        private val repository: WeatherRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(repository) as T
        }
    }
}