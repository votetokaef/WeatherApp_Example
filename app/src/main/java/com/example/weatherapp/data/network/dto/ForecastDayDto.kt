package com.example.weatherapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class ForecastDayDto(
    @SerializedName("avgtemp_c") val temp: Float,
    @SerializedName("condition") val conditionDto: ConditionDto,
)
