package com.example.weatherapp.data.network.api

import com.example.weatherapp.data.network.dto.CityResponseDto
import com.example.weatherapp.data.network.dto.WeatherCurrentResponseDto
import com.example.weatherapp.data.network.dto.WeatherForecastResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("current.json")
    suspend fun loadWeatherCurrent(
        @Query("q") city: String,
    ): WeatherCurrentResponseDto

    @GET("forecast.json")
    suspend fun loadWeatherForecast(
        @Query("q") city: String,
        @Query("days") daysCount: Int = 4,
    ): WeatherForecastResponseDto

    @GET("search.json")
    suspend fun searchCity(
        @Query("q") query: String,
    ): List<CityResponseDto>
}