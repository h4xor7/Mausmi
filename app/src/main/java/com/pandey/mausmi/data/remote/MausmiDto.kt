package com.pandey.mausmi.data.remote

import com.squareup.moshi.Json

data class MausmiDto(
  @field:Json(name = "hourly")
  val mausmiData: MausmiDataDto
)
