package com.example.weatherapp.presentation.extensions

import android.annotation.SuppressLint
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.roundToInt

fun ComponentContext.componentScope(): CoroutineScope = CoroutineScope(
    Dispatchers.Main.immediate + SupervisorJob()
).apply {
    lifecycle.doOnDestroy { cancel() }
}

@SuppressLint("DefaultLocale")
fun Float.formatToCelsius(): String = "${roundToInt()}°C"

fun Calendar.formatToFullDateString(): String = SimpleDateFormat(
    "EEEE | dd MMM yyyy",
    Locale.getDefault()
).format(this.time)

fun Calendar.formatToShortDayString(): String = SimpleDateFormat(
    "EE",
    Locale.getDefault()
).format(this.time)

