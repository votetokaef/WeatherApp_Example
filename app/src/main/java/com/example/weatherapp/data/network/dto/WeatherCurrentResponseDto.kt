package com.example.weatherapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class WeatherCurrentResponseDto(
    @SerializedName("current") val currentContent: CurrentDto,
)