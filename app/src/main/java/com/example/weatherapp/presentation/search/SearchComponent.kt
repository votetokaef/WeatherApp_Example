package com.example.weatherapp.presentation.search

import com.example.weatherapp.domain.entity.City
import kotlinx.coroutines.flow.StateFlow

interface SearchComponent {

    val model: StateFlow<SearchStore.State>

    fun changeSearchQuery(query: String)

    fun clickBack()

    fun clickSearch()

    fun clickCity(city: City)
}