package com.pandey.mausmi.domain.weather

import androidx.annotation.DrawableRes
import com.pandey.mausmi.R

sealed class WeatherType(
  val weatherDesc: String,
  @DrawableRes val iconRes: Int,
  @DrawableRes val backgroundRes: Int,
  @DrawableRes val cardbackRes: Int

  ) {
  object ClearSky : WeatherType(
    weatherDesc = "Clear sky",
    iconRes = R.drawable.ic_sun,
    backgroundRes = R.drawable.gradient_sunny,
    cardbackRes = R.drawable.card_back_sunny
    )

  object MainlyClear : WeatherType(
    weatherDesc = "Mainly clear",
    iconRes = R.drawable.ic_sun,
    backgroundRes = R.drawable.gradient_sunny,
    cardbackRes = R.drawable.card_back_sunny

  )

  object PartlyCloudy : WeatherType(
    weatherDesc = "Partly cloudy",
    iconRes = R.drawable.ic_cloudy,
    backgroundRes = R.drawable.gradient_cloudy,
    cardbackRes = R.drawable.card_back_cloudy

  )

  object Overcast : WeatherType(
    weatherDesc = "Overcast",
    iconRes = R.drawable.ic_cloudy,
    backgroundRes = R.drawable.gradient_cloudy,
    cardbackRes = R.drawable.card_back_cloudy


  )

  object Foggy : WeatherType(
    weatherDesc = "Foggy",
    iconRes = R.drawable.ic_fog,
    backgroundRes = R.drawable.gradient_cloudy,
    cardbackRes = R.drawable.card_back_cloudy


  )

  object DepositingRimeFog : WeatherType(
    weatherDesc = "Depositing rime fog",
    iconRes = R.drawable.ic_rime_fog,
    backgroundRes = R.drawable.gradient_cloudy,
    cardbackRes = R.drawable.card_back_cloudy

  )

  object LightDrizzle : WeatherType(
    weatherDesc = "Light drizzle",
    iconRes = R.drawable.ic_drizzle,
    backgroundRes = R.drawable.gradient_thunder,
    cardbackRes = R.drawable.card_back_thunder


  )

  object ModerateDrizzle : WeatherType(
    weatherDesc = "Moderate drizzle",
    iconRes = R.drawable.ic_drizzle,
    backgroundRes = R.drawable.gradient_thunder,
    cardbackRes = R.drawable.card_back_thunder


  )

  object DenseDrizzle : WeatherType(
    weatherDesc = "Dense drizzle",
    iconRes = R.drawable.ic_drizzle,
    backgroundRes = R.drawable.gradient_thunder,
    cardbackRes = R.drawable.card_back_thunder


  )

  object LightFreezingDrizzle : WeatherType(
    weatherDesc = "Slight freezing drizzle",
    iconRes = R.drawable.ic_drizzle,
    backgroundRes = R.drawable.gradient_thunder,
    cardbackRes = R.drawable.card_back_thunder


  )

  object DenseFreezingDrizzle : WeatherType(
    weatherDesc = "Dense freezing drizzle",
    iconRes = R.drawable.ic_rain,
    backgroundRes = R.drawable.gradient_thunder,
    cardbackRes = R.drawable.card_back_thunder

  )

  object SlightRain : WeatherType(
    weatherDesc = "Slight rain",
    iconRes = R.drawable.ic_rain,
    backgroundRes = R.drawable.gradient_thunder,
    cardbackRes = R.drawable.card_back_thunder


  )

  object ModerateRain : WeatherType(
    weatherDesc = "Rainy",
    iconRes = R.drawable.ic_rain,
    backgroundRes = R.drawable.gradient_thunder,
    cardbackRes = R.drawable.card_back_thunder


  )

  object HeavyRain : WeatherType(
    weatherDesc = "Heavy rain",
    iconRes = R.drawable.ic_heavy_rain,
    backgroundRes = R.drawable.gradient_thunder,
    cardbackRes = R.drawable.card_back_thunder


  )

  object HeavyFreezingRain : WeatherType(
    weatherDesc = "Heavy freezing rain",
    iconRes = R.drawable.ic_heavy_rain,
    backgroundRes = R.drawable.gradient_thunder,
    cardbackRes = R.drawable.card_back_thunder


  )

  object SlightSnowFall : WeatherType(
    weatherDesc = "Slight snow fall",
    iconRes = R.drawable.ic_snow_fall,
    backgroundRes = R.drawable.gradient_thunder,
    cardbackRes = R.drawable.card_back_thunder


  )

  object ModerateSnowFall : WeatherType(
    weatherDesc = "Moderate snow fall",
    iconRes = R.drawable.ic_snow_fall,
    backgroundRes = R.drawable.gradient_thunder,
    cardbackRes = R.drawable.card_back_thunder


  )

  object HeavySnowFall : WeatherType(
    weatherDesc = "Heavy snow fall",
    iconRes = R.drawable.ic_snow_fall,
    backgroundRes = R.drawable.gradient_thunder,
    cardbackRes = R.drawable.card_back_thunder


  )

  object SnowGrains : WeatherType(
    weatherDesc = "Snow grains",
    iconRes = R.drawable.ic_snow_fall,
    backgroundRes = R.drawable.gradient_thunder,
    cardbackRes = R.drawable.card_back_thunder


  )

  object SlightRainShowers : WeatherType(
    weatherDesc = "Slight rain showers",
    iconRes = R.drawable.ic_rain,
    backgroundRes = R.drawable.gradient_thunder,
    cardbackRes = R.drawable.card_back_thunder


  )

  object ModerateRainShowers : WeatherType(
    weatherDesc = "Moderate rain showers",
    iconRes = R.drawable.ic_rain,
    backgroundRes = R.drawable.gradient_thunder,
    cardbackRes = R.drawable.card_back_thunder


  )

  object ViolentRainShowers : WeatherType(
    weatherDesc = "Violent rain showers",
    iconRes = R.drawable.ic_heavy_rain,
    backgroundRes = R.drawable.gradient_thunder,
    cardbackRes = R.drawable.card_back_thunder


  )

  object SlightSnowShowers : WeatherType(
    weatherDesc = "Light snow showers",
    iconRes = R.drawable.ic_snow_fall,
    backgroundRes = R.drawable.gradient_thunder,
    cardbackRes = R.drawable.card_back_thunder


  )

  object HeavySnowShowers : WeatherType(
    weatherDesc = "Heavy snow showers",
    iconRes = R.drawable.ic_snow_fall,
    backgroundRes = R.drawable.gradient_thunder,
    cardbackRes = R.drawable.card_back_thunder


  )

  object ModerateThunderstorm : WeatherType(
    weatherDesc = "Moderate thunderstorm",
    iconRes = R.drawable.ic_thunder,
    backgroundRes = R.drawable.gradient_thunder,
    cardbackRes = R.drawable.card_back_thunder


  )

  object SlightHailThunderstorm : WeatherType(
    weatherDesc = "Thunderstorm with slight hail",
    iconRes = R.drawable.ic_thunder,
    backgroundRes = R.drawable.gradient_thunder,
    cardbackRes = R.drawable.card_back_thunder


  )

  object HeavyHailThunderstorm : WeatherType(
    weatherDesc = "Thunderstorm with heavy hail",
    iconRes = R.drawable.ic_thunder,
    backgroundRes = R.drawable.gradient_thunder,
    cardbackRes = R.drawable.card_back_thunder

  )

  companion object {
    fun fromWMO(code: Int): WeatherType {
      return when (code) {
        0 -> ClearSky
        1 -> MainlyClear
        2 -> PartlyCloudy
        3 -> Overcast
        45 -> Foggy
        48 -> DepositingRimeFog
        51 -> LightDrizzle
        53 -> ModerateDrizzle
        55 -> DenseDrizzle
        56 -> LightFreezingDrizzle
        57 -> DenseFreezingDrizzle
        61 -> SlightRain
        63 -> ModerateRain
        65 -> HeavyRain
        66 -> LightFreezingDrizzle
        67 -> HeavyFreezingRain
        71 -> SlightSnowFall
        73 -> ModerateSnowFall
        75 -> HeavySnowFall
        77 -> SnowGrains
        80 -> SlightRainShowers
        81 -> ModerateRainShowers
        82 -> ViolentRainShowers
        85 -> SlightSnowShowers
        86 -> HeavySnowShowers
        95 -> ModerateThunderstorm
        96 -> SlightHailThunderstorm
        99 -> HeavyHailThunderstorm
        else -> ClearSky
      }
    }
  }
}