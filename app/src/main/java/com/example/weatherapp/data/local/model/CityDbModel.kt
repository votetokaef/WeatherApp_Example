package com.example.weatherapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_cities")
data class CityDbModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val country: String,
)