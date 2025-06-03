package com.example.weatherapp.presentation.favourite

import com.example.weatherapp.domain.entity.City
import kotlinx.coroutines.flow.StateFlow

interface FavouriteComponent {

    val model: StateFlow<FavouriteStore.State>

    fun clickSearch()

    fun clickToFavourite()

    fun cityItemClicked(city: City)

}