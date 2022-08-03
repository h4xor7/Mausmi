package com.pandey.mausmi.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.pandey.mausmi.data.mappers.toWeatherInfo
import com.pandey.mausmi.data.remote.MausmiApi
import com.pandey.mausmi.domain.repository.MausmiRepository
import com.pandey.mausmi.domain.util.Resource
import com.pandey.mausmi.domain.weather.WeatherInfo
import javax.inject.Inject

class MausmiRepositoryImpl @Inject constructor( private val api: MausmiApi) :MausmiRepository {

  @RequiresApi(Build.VERSION_CODES.O)
  override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
    return try {
      Resource.Success(
        data = api.getWeatherData(
          lat = lat,
          long = long
        ).toWeatherInfo()
      )
    } catch(e: Exception) {
      e.printStackTrace()
      Resource.Error(e.message ?: "An unknown error occurred.")
    }

  }
}