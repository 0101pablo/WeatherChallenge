package com.example.weatherchallenge.infra.provider

import com.example.weatherchallenge.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkUtils {

    companion object {
        fun getRetrofitInstance(path: String): Retrofit {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val client = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
            if (BuildConfig.DEBUG) {
                client.addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
            return Retrofit.Builder()
                .baseUrl(path)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client.build())
                .build()
        }
    }
}