package com.example.weatherapp.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.weatherapp.domain.entity.Forecast
import com.example.weatherapp.domain.entity.Weather
import com.example.weatherapp.presentation.extensions.formatToCelsius
import com.example.weatherapp.presentation.extensions.formatToFullDateString
import com.example.weatherapp.presentation.extensions.formatToShortDayString
import com.example.weatherapp.presentation.ui.theme.CardGradients

@Composable
fun DetailsContent(component: DetailsComponent) {

    val state: DetailsStore.State by component.model.collectAsState()

    Scaffold(
        containerColor = Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
            .background(CardGradients.gradients[1].primaryGradient),
        topBar = {
            TopDetailBar(
                cityName = state.city.name,
                isFavourite = state.isFavourite,
                onBackClick = { component.clickBack() },
                onFavouriteChangeStatusClick = { component.clickChangeFavouriteStatus() }
            )
        },
        contentWindowInsets = WindowInsets.statusBars
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
        ) {
            when (val forecastState = state.forecastState) {
                DetailsStore.State.ForecastState.Error -> {

                }

                DetailsStore.State.ForecastState.Initial -> {

                }

                is DetailsStore.State.ForecastState.Loaded -> {
                    Loaded(forecastState.forecast)
                }

                DetailsStore.State.ForecastState.Loading -> {
                    Loading()
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun Loaded(
    forecast: Forecast,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .background(Color.Transparent),
                text = forecast.currentWeather.conditionText,
                color = MaterialTheme.colorScheme.background,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = forecast.currentWeather.tempC.formatToCelsius(),
                    color = MaterialTheme.colorScheme.background,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 64.sp)
                )
                GlideImage(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    model = forecast.currentWeather.conditionUrl,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = forecast.currentWeather.date.formatToFullDateString(),
                color = MaterialTheme.colorScheme.background,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.LightGray.copy(alpha = 0.2f)),
            shape = MaterialTheme.shapes.extraLarge,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Upcoming",
                    color = MaterialTheme.colorScheme.background,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 28.sp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.aligned(Alignment.CenterHorizontally),
                ) {
                    forecast.upcoming.forEach { weather ->
                        ForecastDayCard(weather)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ForecastDayCard(
    weather: Weather,
) {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = MaterialTheme.shapes.small,
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(4.dp)
                .sizeIn(minWidth = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = weather.tempC.formatToCelsius(), fontSize = 18.sp)
            GlideImage(
                modifier = Modifier.size(64.dp),
                model = weather.conditionUrl,
                contentDescription = null
            )
            Text(text = weather.date.formatToShortDayString(), fontSize = 18.sp)
        }
    }
}

@Composable
private fun Loading(
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .size(54.dp),
            color = MaterialTheme.colorScheme.background
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopDetailBar(
    cityName: String,
    isFavourite: Boolean,
    onBackClick: () -> Unit,
    onFavouriteChangeStatusClick: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = { Text(text = cityName) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.background
        ),
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = { onFavouriteChangeStatusClick() }) {
                val icon = if (isFavourite) {
                    Icons.Default.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                }
                Icon(
                    imageVector = icon,
                    contentDescription = null
                )
            }
        }
    )

}