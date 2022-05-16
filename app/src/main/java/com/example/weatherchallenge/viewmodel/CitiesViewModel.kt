package com.example.weatherchallenge.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherchallenge.domain.CityModel
import com.example.weatherchallenge.domain.ReverseGeoCodeModel
import com.example.weatherchallenge.infra.repositories.CitiesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CitiesViewModel(private val repository: CitiesRepository): ViewModel() {

    val citiesLiveData = MutableLiveData<List<CityModel>>()

    fun getCities() {
        CoroutineScope(Dispatchers.Main).launch {
            val cities = withContext(Dispatchers.Default) {
                repository.getCities()
            }
            citiesLiveData.value = cities
        }
    }

    class CitiesViewModelFactory(
        private val repository: CitiesRepository
    ): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return CitiesViewModel(repository) as T
        }
    }
}