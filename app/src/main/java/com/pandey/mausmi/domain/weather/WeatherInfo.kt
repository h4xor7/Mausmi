package com.pandey.mausmi.domain.weather

data class WeatherInfo(
  val weatherDataPerDay: Map<Int, List<WeatherData>>,
  val currentWeatherData: WeatherData?
)
