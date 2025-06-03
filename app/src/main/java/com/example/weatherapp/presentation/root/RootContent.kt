package com.example.weatherapp.presentation.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.example.weatherapp.presentation.details.DetailsContent
import com.example.weatherapp.presentation.favourite.FavouriteContent
import com.example.weatherapp.presentation.search.SearchContent
import com.example.weatherapp.presentation.ui.theme.WeatherAppTheme

@Composable
fun RootContent(
    component: RootComponent,
) {
    WeatherAppTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Children(
                stack = component.stack
            ) {
                when (val instance = it.instance) {
                    is RootComponent.Child.Details -> {
                        DetailsContent(component = instance.component)
                    }

                    is RootComponent.Child.Favourite -> {
                        FavouriteContent(component = instance.component)
                    }

                    is RootComponent.Child.Search -> {
                        SearchContent(component = instance.component)
                    }
                }
            }
        }

    }
}