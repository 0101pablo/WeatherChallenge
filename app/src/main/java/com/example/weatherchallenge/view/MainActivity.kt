package com.example.weatherchallenge.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherchallenge.R
import com.example.weatherchallenge.databinding.ActivityMainBinding
import com.example.weatherchallenge.view.ui.CitiesFragment

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (binding == null) {
            binding = ActivityMainBinding.inflate(layoutInflater)
        }
        setContentView(binding?.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.container, CitiesFragment())
                .commit()
        }
    }
}