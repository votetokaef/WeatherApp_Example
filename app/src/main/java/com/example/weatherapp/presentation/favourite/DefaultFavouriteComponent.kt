package com.example.weatherapp.presentation.favourite

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.example.weatherapp.domain.entity.City
import com.example.weatherapp.presentation.extensions.componentScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultFavouriteComponent @AssistedInject constructor(
    private val favouriteStoreFactory: FavouriteStoreFactory,
    @Assisted("onCityItemClicked") private val onCityItemClicked: (City) -> Unit,
    @Assisted("onClickSearch") private val onClickSearch: () -> Unit,
    @Assisted("onClickToFavourite") private val onClickToFavourite: () -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext,
) : FavouriteComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { favouriteStoreFactory.create() }

    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    is FavouriteStore.Label.CityItemClicked -> {
                        onCityItemClicked(it.city)
                    }

                    FavouriteStore.Label.ClickSearch -> {
                        onClickSearch()
                    }

                    FavouriteStore.Label.ClickToFavourite -> {
                        onClickToFavourite()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<FavouriteStore.State>
        get() = store.stateFlow

    override fun clickSearch() {
        store.accept(FavouriteStore.Intent.ClickSearch)
    }

    override fun clickToFavourite() {
        store.accept(FavouriteStore.Intent.ClickToFavourite)
    }

    override fun cityItemClicked(city: City) {
        store.accept(FavouriteStore.Intent.CityItemClicked(city))
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("onCityItemClicked") onCityItemClicked: (City) -> Unit,
            @Assisted("onClickSearch") onClickSearch: () -> Unit,
            @Assisted("onClickToFavourite") onClickToFavourite: () -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext,
        ): DefaultFavouriteComponent
    }
}