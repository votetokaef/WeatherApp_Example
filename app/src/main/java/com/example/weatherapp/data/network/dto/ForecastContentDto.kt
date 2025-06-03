package com.example.weatherapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class ForecastContentDto(
    @SerializedName("date_epoch") val forecastDate: Long,
    @SerializedName("day") val forecastDay: ForecastDayDto,
)