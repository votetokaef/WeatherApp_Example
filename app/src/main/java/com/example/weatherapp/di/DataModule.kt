package com.example.weatherapp.di

import android.content.Context
import com.example.weatherapp.data.local.db.FavouriteCitiesDao
import com.example.weatherapp.data.local.db.FavouriteDatabase
import com.example.weatherapp.data.network.api.ApiFactory
import com.example.weatherapp.data.network.api.ApiService
import com.example.weatherapp.data.repository.DefaultFavouriteRepository
import com.example.weatherapp.data.repository.DefaultSearchRepository
import com.example.weatherapp.data.repository.DefaultWeatherRepository
import com.example.weatherapp.domain.repository.FavouriteRepository
import com.example.weatherapp.domain.repository.SearchRepository
import com.example.weatherapp.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindFavouriteRepository(impl: DefaultFavouriteRepository): FavouriteRepository

    @ApplicationScope
    @Binds
    fun bindSearchRepository(impl: DefaultSearchRepository): SearchRepository

    @ApplicationScope
    @Binds
    fun bindWeatherRepository(impl: DefaultWeatherRepository): WeatherRepository

    companion object {

        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService = ApiFactory.apiService

        @ApplicationScope
        @Provides
        fun provideFavouriteCitiesDao(context: Context): FavouriteCitiesDao {
            return FavouriteDatabase.getInstance(context).favouriteCitiesDao()
        }
    }
}