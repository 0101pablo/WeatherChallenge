package com.example.weatherchallenge.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherchallenge.databinding.CityItemBinding
import com.example.weatherchallenge.domain.CityModel
import com.example.weatherchallenge.view.adapter.holder.CitiesViewHolder

class CitiesAdapter(private val cities: List<CityModel>) :
    RecyclerView.Adapter<CitiesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder {
        val itemBinding =
            CityItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CitiesViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        holder.bind(cities[position])
    }

    override fun getItemCount(): Int {
        return cities.size
    }
}