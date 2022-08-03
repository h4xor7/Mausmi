package com.pandey.mausmi.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandey.mausmi.domain.location.LocationTracker
import com.pandey.mausmi.domain.repository.MausmiRepository
import com.pandey.mausmi.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MausmiViewModel @Inject constructor(
  private val mausmiRepository: MausmiRepository,
  private val locationTracker: LocationTracker
) : ViewModel() {
  private val _uiState = MutableStateFlow(MausmiState())
  val uiState: StateFlow<MausmiState> get() = _uiState

  fun loadWeatherInfo() {
    viewModelScope.launch {
      _uiState.value = MausmiState(isLoading = true, error = null)

      locationTracker.getCurrentLocation()?.let { location ->
        when (val result = mausmiRepository.getWeatherData(location.latitude, location.longitude)) {
          is Resource.Success -> {
            _uiState.value = MausmiState(weatherInfo = result.data, isLoading = false, error = null)
            Log.d(Companion.TAG, "loadWeatherInfo: Succees ${result.data} ")
          }
          is Resource.Error -> {
            _uiState.value = MausmiState(weatherInfo = null, isLoading = false, error = result.message)
            Log.d(Companion.TAG, "loadWeatherInfo: Error ${result.message} ")

          }
          else -> {
            _uiState.value = MausmiState(weatherInfo = null, isLoading = false, error = result.message)
            Log.d(Companion.TAG, "loadWeatherInfo: Loading ${result.message} ")

          }
        }

      }?:kotlin.run {
        _uiState.value = MausmiState(weatherInfo = null, isLoading = false, error = "Couldn't retrieve location. Make sure to grant permission and enable GPS.")
        Log.d(Companion.TAG, "loadWeatherInfo: error bhr")

      }
    }
  }

  companion object {
    private const val TAG = "MausmiViewModel"
  }


}