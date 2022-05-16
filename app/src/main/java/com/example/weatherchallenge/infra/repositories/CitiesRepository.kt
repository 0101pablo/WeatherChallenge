package com.example.weatherchallenge.infra.repositories

import com.example.weatherchallenge.domain.CityModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class CitiesRepository {

    suspend fun getCities(): List<CityModel> {
        return withContext(Dispatchers.Default) {
            delay(1000)
            listOf(
                CityModel("Localização atual", "", 0f, 0f, true),
                CityModel("Berlim", "DE", 52.5170365f, 13.3888599f),
                CityModel("Copenhaga", "DK", 55.6867243f, 12.5700724f),
                CityModel("Dublin", "IE", 53.3497645f, -6.2602732f),
                CityModel("Lisboa", "PT", 38.7077507f, -9.1365919f),
                CityModel("Londres", "GB", 51.5073219f, -0.1276474f),
                CityModel("Madrid", "ES", 40.4167047f, -3.7035825f),
                CityModel("Paris", "FR", 48.8588897f, 2.3200410217200766f),
                CityModel("Praga", "CZ", 50.0874654f, 14.4212535f),
                CityModel("Roma", "IT", 41.8933203f, 12.4829321f),
                CityModel("Viena", "AT", 48.2083537f, 16.3725042f)
            )
        }
    }
}