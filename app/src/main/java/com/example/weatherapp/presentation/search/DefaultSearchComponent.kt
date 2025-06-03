package com.example.weatherapp.presentation.search

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

class DefaultSearchComponent @AssistedInject constructor(
    private val searchStoreFactory: SearchStoreFactory,
    @Assisted("openReason") private val openReason: OpenReason,
    @Assisted("onClickBack") private val onClickBack: () -> Unit,
    @Assisted("onOpenForecast") private val onOpenForecast: (City) -> Unit,
    @Assisted("onSavedToFavourite") private val onSavedToFavourite: () -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext,
) : SearchComponent, ComponentContext by componentContext {

    private val store: SearchStore =
        instanceKeeper.getStore { searchStoreFactory.create(openReason) }

    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    SearchStore.Label.ClickBack -> {
                        onClickBack()
                    }

                    is SearchStore.Label.OpenForecast -> {
                        onOpenForecast(it.city)
                    }

                    SearchStore.Label.SavedToFavourite -> {
                        onSavedToFavourite()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<SearchStore.State>
        get() = store.stateFlow

    override fun changeSearchQuery(query: String) {
        store.accept(SearchStore.Intent.ChangeSearchQuery(query))
    }

    override fun clickBack() {
        store.accept(SearchStore.Intent.ClickBack)

    }

    override fun clickSearch() {
        store.accept(SearchStore.Intent.ClickSearch)

    }

    override fun clickCity(city: City) {
        store.accept(SearchStore.Intent.ClickCity(city))
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("openReason") openReason: OpenReason,
            @Assisted("onClickBack") onClickBack: () -> Unit,
            @Assisted("onOpenForecast") onOpenForecast: (City) -> Unit,
            @Assisted("onSavedToFavourite") onSavedToFavourite: () -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext,
        ): DefaultSearchComponent
    }
}