package com.example.weatherchallenge.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherchallenge.databinding.FragmentCitiesBinding
import com.example.weatherchallenge.infra.repositories.CitiesRepository
import com.example.weatherchallenge.view.adapter.CitiesAdapter
import com.example.weatherchallenge.viewmodel.CitiesViewModel

class CitiesFragment : Fragment() {

    private var binding: FragmentCitiesBinding? = null
    private lateinit var viewModel: CitiesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            binding = FragmentCitiesBinding.inflate(inflater, container, false)
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding?.rvCities
        recyclerView?.layoutManager = LinearLayoutManager(activity)

        viewModel = ViewModelProvider(
            this,
            CitiesViewModel.CitiesViewModelFactory(CitiesRepository())
        )[CitiesViewModel::class.java]

        viewModel.citiesLiveData.observe(viewLifecycleOwner) { cities ->
            recyclerView?.adapter = CitiesAdapter(cities)
        }
        viewModel.getCities()
    }
}