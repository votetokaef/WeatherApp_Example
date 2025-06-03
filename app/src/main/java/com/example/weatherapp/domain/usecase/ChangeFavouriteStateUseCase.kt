package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.entity.City
import com.example.weatherapp.domain.repository.FavouriteRepository
import javax.inject.Inject

class ChangeFavouriteStateUseCase @Inject constructor(
    private val repository: FavouriteRepository,
) {

    suspend fun addToFavourites(city: City) = repository.addToFavourite(city)

    suspend fun removeFromFavourites(cityId: Int) = repository.removeFromFavourite(cityId)
}