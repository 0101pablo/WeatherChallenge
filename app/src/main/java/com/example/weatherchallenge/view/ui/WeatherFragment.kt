package com.example.weatherchallenge.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherchallenge.R
import com.example.weatherchallenge.databinding.FragmentWeatherBinding
import com.example.weatherchallenge.domain.CityModel
import com.example.weatherchallenge.infra.repositories.WeatherRepository
import com.example.weatherchallenge.viewmodel.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient


class WeatherFragment : Fragment() {

    private var binding: FragmentWeatherBinding? = null
    private lateinit var viewModel: WeatherViewModel
    private var cityBundle: CityModel? = null

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            binding = FragmentWeatherBinding.inflate(inflater, container, false)
        }
        arguments?.let { args ->
            args.getString(CityModel.NAME)?.let {
                cityBundle = CityModel(
                    args.getString(CityModel.NAME),
                    args.getString(CityModel.COUNTRY),
                    args.getFloat(CityModel.LAT),
                    args.getFloat(CityModel.LON),
                    args.getBoolean(CityModel.CURRENT_LOCATION)
                )
            }
        }

        context?.let {
            fusedLocationClient = getFusedLocationProviderClient(it)
        }

        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this, WeatherViewModel.WeatherViewModelFactory(WeatherRepository())
        )[WeatherViewModel::class.java]

        if (cityBundle?.currentLocation == true) {

//            context?.let {
//                viewModel.getLastKnownLocation(it)
//            }
//
//            viewModel.locationsLiveData.observe(viewLifecycleOwner) {
//                viewModel.locationsLiveData.value?.let {
//                    viewModel.getReverseGeoCode(it[0], it[1])
//                }
//            }


            viewModel.reverseGeoCodeLiveData.observe(viewLifecycleOwner) { reverseGeoCode ->
                reverseGeoCode?.get(0)?.let { reverseGeo ->
                    cityBundle?.name = reverseGeo.localNames.cityNamePT
                    cityBundle?.lat = reverseGeo.lat
                    cityBundle?.lon = reverseGeo.lon
                }
            }
        }

        cityBundle?.let {
            viewModel.getWeather(it)
        } ?: kotlin.run {
            Toast.makeText(
                context,
                getString(R.string.data_process_failed_message),
                Toast.LENGTH_LONG
            ).show()
        }
        viewModel.weatherLiveData.observe(viewLifecycleOwner) { weather ->
            weather?.let {
                binding?.tvCityName?.text = it.cityName
                binding?.tvWeatherDescription?.text = it.weather[0].description
                binding?.tvTemp?.text =
                    getString(R.string.value_with_celsius, String.format("%.1f", it.main.temp))
                binding?.tvFeelsLike?.text =
                    getString(R.string.value_with_celsius, String.format("%.1f", it.main.feelsLike))
                binding?.tvTempMin?.text =
                    getString(R.string.value_with_celsius, String.format("%.1f", it.main.tempMin))
                binding?.tvTempMax?.text =
                    getString(R.string.value_with_celsius, String.format("%.1f", it.main.tempMax))
                binding?.tvPressure?.text =
                    getString(R.string.value_with_hpa, String.format("%.0f", it.main.pressure))
                binding?.tvHumidity?.text =
                    getString(R.string.value_with_percent, String.format("%.0f", it.main.humidity))
                binding?.tvWindSpeed?.text =
                    getString(
                        R.string.value_with_meters_per_second,
                        String.format("%.1f", it.wind.speed)
                    )
                binding?.tvWindDeg?.text = String.format("%.0f", it.wind.deg)
            } ?: kotlin.run {
                binding?.tvCityName?.text = getString(R.string.load_data_error_message)
            }
        }
    }

//    private suspend fun getLastKnownLocation(): ArrayList<Float> = suspendCoroutine { cont ->
//        context?.let {
//            if (ContextCompat.checkSelfPermission(
//                    it, Manifest.permission.ACCESS_COARSE_LOCATION
//                ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
//                    it, Manifest.permission.ACCESS_FINE_LOCATION
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
//                    }, Looper.getMainLooper())
//            }
//        }
//    }
}