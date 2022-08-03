package com.pandey.mausmi.data.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.pandey.mausmi.data.remote.MausmiDataDto
import com.pandey.mausmi.data.remote.MausmiDto
import com.pandey.mausmi.domain.weather.WeatherData
import com.pandey.mausmi.domain.weather.WeatherInfo
import com.pandey.mausmi.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherData(
  val index: Int,
  val data: WeatherData
)

@RequiresApi(Build.VERSION_CODES.O)
fun MausmiDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
  return time.mapIndexed { index, time ->
    val temperature = temperatures[index]
    val weatherCode = weatherCodes[index]
    val windSpeed = windSpeeds[index]
    val pressure = pressures[index]
    val humidity = humidities[index]
    IndexedWeatherData(
      index = index,
      data = WeatherData(
        time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
        temperatureCelsius = temperature,
        pressure = pressure,
        windSpeed = windSpeed,
        humidity = humidity,
        weatherType = WeatherType.fromWMO(weatherCode)
      )
    )
  }.groupBy {
    it.index / 24
  }.mapValues {
    it.value.map { it.data }
  }
}

@RequiresApi(Build.VERSION_CODES.O)
fun MausmiDto.toWeatherInfo(): WeatherInfo {
  val weatherDataMap = mausmiData.toWeatherDataMap()
  val now = LocalDateTime.now()
  val currentWeatherData = weatherDataMap[0]?.find {
    val hour = if(now.minute < 30) now.hour else now.hour + 1
    it.time.hour == hour
  }
  return WeatherInfo(
    weatherDataPerDay = weatherDataMap,
    currentWeatherData = currentWeatherData
  )
}