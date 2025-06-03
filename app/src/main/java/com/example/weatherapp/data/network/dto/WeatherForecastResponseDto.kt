package com.example.weatherapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class WeatherForecastResponseDto(
    @SerializedName("current") val currentDto: CurrentDto,
    @SerializedName("forecast") val forecastDto: ForecastDto,
)