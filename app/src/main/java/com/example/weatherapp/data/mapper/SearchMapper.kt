package com.example.weatherapp.data.mapper

import com.example.weatherapp.data.network.dto.CityResponseDto
import com.example.weatherapp.domain.entity.City

fun CityResponseDto.toEntity(): City = City(id, name, country)

fun List<CityResponseDto>.toEntities(): List<City> = map {
    it.toEntity()
}