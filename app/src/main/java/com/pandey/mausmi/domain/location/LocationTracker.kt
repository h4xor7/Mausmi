package com.pandey.mausmi.domain.location

import android.location.Location

interface LocationTracker {
  suspend fun getCurrentLocation(): Location?
}