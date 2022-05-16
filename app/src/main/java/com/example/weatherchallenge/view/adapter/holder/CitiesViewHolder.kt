package com.example.weatherchallenge.view.adapter.holder

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherchallenge.R
import com.example.weatherchallenge.databinding.CityItemBinding
import com.example.weatherchallenge.domain.CityModel
import com.example.weatherchallenge.view.ui.WeatherFragment

class CitiesViewHolder(private val itemBinding: CityItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    private var cityModel: CityModel? = null

    fun bind(cityModel: CityModel) {
        this.cityModel = cityModel
        itemBinding.tvCity.text = cityModel.name
    }

    init {
        itemBinding.tvCity.setOnClickListener {

            when (true) {
                !isLocalAtual() -> goToWeatherFragment()
                !isPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                    requestPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                }
                !isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    requestPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                }
                else -> goToWeatherFragment()
            }
        }
    }

    private fun isLocalAtual(): Boolean {
        return cityModel?.currentLocation == true
    }

    private fun isPermissionGranted(permissionType: String): Boolean {
        return ContextCompat.checkSelfPermission(
            itemView.context, permissionType
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(permissionType: String) {
        ActivityCompat.requestPermissions(
            itemView.context as Activity,
            arrayOf(permissionType), 1
        )
    }

    private fun goToWeatherFragment() {
        val bundle = Bundle()
        bundle.putString(CityModel.NAME, cityModel?.name)
        bundle.putString(CityModel.COUNTRY, cityModel?.country)
        cityModel?.lat?.let { bundle.putFloat(CityModel.LAT, it) }
        cityModel?.lon?.let { bundle.putFloat(CityModel.LON, it) }
        bundle.putBoolean(CityModel.CURRENT_LOCATION, cityModel?.currentLocation ?: false)
        val fragment = WeatherFragment()
        fragment.arguments = bundle
        val activity: AppCompatActivity = itemView.context as AppCompatActivity
        activity.supportFragmentManager.beginTransaction().setCustomAnimations(
            R.anim.slide_in,
            R.anim.fade_out,
            R.anim.fade_in,
            R.anim.slide_out
        ).replace(R.id.container, fragment).addToBackStack(null).commit()
    }
}