package com.example.weatherapp.presentation.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.example.weatherapp.domain.entity.City
import com.example.weatherapp.presentation.details.DefaultDetailsComponent
import com.example.weatherapp.presentation.favourite.DefaultFavouriteComponent
import com.example.weatherapp.presentation.search.DefaultSearchComponent
import com.example.weatherapp.presentation.search.OpenReason
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.serialization.Serializable

class DefaultRootComponent @AssistedInject constructor(
    private val detailsComponentFactory: DefaultDetailsComponent.Factory,
    private val favouriteComponentFactory: DefaultFavouriteComponent.Factory,
    private val searchComponentFactory: DefaultSearchComponent.Factory,
    @Assisted("componentContext") componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Favourite,
        handleBackButton = true,
        childFactory = ::child
    )

    @OptIn(DelicateDecomposeApi::class)
    private fun child(
        config: Config,
        componentContext: ComponentContext,
    ): RootComponent.Child {
        return when (config) {
            is Config.Details -> {
                val component = detailsComponentFactory.create(
                    city = config.city,
                    onClickBack = {
                        navigation.pop()
                        "555"
                    },
                    componentContext = componentContext
                )
                RootComponent.Child.Details(component)
            }

            Config.Favourite -> {
                val component = favouriteComponentFactory.create(
                    onCityItemClicked = {
                        navigation.push(Config.Details(it))
                    },
                    onClickSearch = {
                        navigation.push(Config.Search(openReason = OpenReason.RegularSearch))
                    },
                    onClickToFavourite = {
                        navigation.push(Config.Search(openReason = OpenReason.AddToFavourite))
                    },
                    componentContext = componentContext
                )
                RootComponent.Child.Favourite(component)
            }

            is Config.Search -> {
                val component = searchComponentFactory.create(
                    openReason = config.openReason,
                    onClickBack = {
                        navigation.pop()
                    },
                    onOpenForecast = {
                        navigation.push(Config.Details(it))
                    },
                    onSavedToFavourite = {
                        navigation.pop()
                    },
                    componentContext = componentContext
                )
                RootComponent.Child.Search(component)
            }
        }

    }

    @Serializable
    sealed interface Config {

        @Serializable
        data class Details(val city: City) : Config

        @Serializable
        data object Favourite : Config

        @Serializable
        data class Search(val openReason: OpenReason) : Config
    }

    @AssistedFactory
    interface Factory {

        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
        ): DefaultRootComponent
    }
}