package com.example.weatherapp.presentation.details

import kotlinx.coroutines.flow.StateFlow

interface DetailsComponent {

    val model: StateFlow<DetailsStore.State>

    fun clickChangeFavouriteStatus()

    fun clickBack()
}