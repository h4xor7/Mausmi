package com.pandey.mausmi.presentation

import com.pandey.mausmi.domain.weather.WeatherInfo

data class MausmiState(
  val weatherInfo: WeatherInfo? = null,
  val isLoading: Boolean = false,
  val error: String? = null
)
