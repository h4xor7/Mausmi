package com.pandey.mausmi.domain.repository

import com.pandey.mausmi.domain.util.Resource
import com.pandey.mausmi.domain.weather.WeatherInfo

interface MausmiRepository {

  suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}