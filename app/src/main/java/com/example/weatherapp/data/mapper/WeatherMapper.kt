package com.example.weatherapp.data.mapper

import com.example.weatherapp.data.network.dto.CurrentDto
import com.example.weatherapp.data.network.dto.ForecastContentDto
import com.example.weatherapp.data.network.dto.WeatherCurrentResponseDto
import com.example.weatherapp.data.network.dto.WeatherForecastResponseDto
import com.example.weatherapp.domain.entity.Forecast
import com.example.weatherapp.domain.entity.Weather
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun WeatherCurrentResponseDto.toEntity(): Weather {
    return Weather(
        tempC = currentContent.tempC,
        conditionText = currentContent.conditionDto.text,
        conditionUrl = currentContent.conditionDto.iconUrl.correctImageUrl(),
        date = currentContent.date.toCalendar()
    )
}

fun CurrentDto.toEntity(): Weather {
    return Weather(
        tempC = tempC,
        conditionText = conditionDto.text,
        conditionUrl = conditionDto.iconUrl.correctImageUrl(),
        date = date.toCalendar()
    )
}

fun ForecastContentDto.toEntity(): Weather {
    return Weather(
        tempC = forecastDay.temp,
        conditionText = forecastDay.conditionDto.text,
        conditionUrl = forecastDay.conditionDto.iconUrl.correctImageUrl(),
        date = forecastDate.toCalendar()
    )
}

fun List<ForecastContentDto>.toEntity(): List<Weather> = map { it.toEntity() }

fun WeatherForecastResponseDto.toEntity(): Forecast {
    return Forecast(
        currentWeather = currentDto.toEntity(),
        upcoming = forecastDto.forecastContentDto.drop(1).toEntity()
    )
}

private fun String.correctImageUrl() = "https:$this".replace(
    oldValue = "64x64",
    newValue = "128x128"
)

private fun Long.toCalendar() = Calendar.getInstance(Locale.getDefault()).apply {
    time = Date(this@toCalendar * TO_MILLIS)
}

private const val TO_MILLIS = 1000

//fun epochToCalendar(epoch: Long, locale: Locale = Locale.getDefault()): Calendar {
//    val calendar = Calendar.getInstance(locale)
//    calendar.timeInMillis = epoch * TO_MILLIS
//    return calendar
//}