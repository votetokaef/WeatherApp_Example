package com.example.weatherapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import com.example.weatherapp.WeatherApplication
import com.example.weatherapp.presentation.root.DefaultRootComponent
import com.example.weatherapp.presentation.root.RootContent
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var rootComponentFactory: DefaultRootComponent.Factory

//    @Inject
//    lateinit var searchCityUsecase: SearchCityUsecase
//
//    @Inject
//    lateinit var changeFavouriteStateUseCase: ChangeFavouriteStateUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as WeatherApplication).applicationComponent.inject(this)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        val scope = CoroutineScope(Dispatchers.Default)
//        scope.launch {
//            searchCityUsecase("мон").forEach{
//                changeFavouriteStateUseCase.addToFavourites(it)
//            }
//        }

        val root = rootComponentFactory.create(
            componentContext = defaultComponentContext()
        )
        setContent {
            RootContent(
                component = root
            )
        }
    }
}
