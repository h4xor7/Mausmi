package com.pandey.mausmi.presentation

import android.Manifest
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.pandey.mausmi.databinding.ActivityMainBinding
import com.pandey.mausmi.domain.location.LocationTracker
import com.pandey.mausmi.domain.weather.WeatherData
import com.pandey.mausmi.domain.weather.WeatherType
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private val viewModel: MausmiViewModel by viewModels()
  private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
  private lateinit var mainAdapter: MainAdapter
  @Inject
  lateinit var locationTracker: LocationTracker

  @RequiresApi(Build.VERSION_CODES.O)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)

    val view = binding.root
    setContentView(view)


    permissionLauncher =
      registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        viewModel.loadWeatherInfo();
      }
    permissionLauncher.launch(
      arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
      )
    )

    setData()

   // setAddress()
  }

  private fun setupRecyclerView(listdata: List<WeatherData>?, weatherType: WeatherType?) {
    val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    binding.rvMain.layoutManager = layoutManager
    mainAdapter = MainAdapter(this, weatherType)
    if (listdata != null) {
      mainAdapter.setWeatherData(listdata)
    }
    binding.rvMain.adapter = mainAdapter

  }


  @RequiresApi(Build.VERSION_CODES.O)
  fun setData() {

    lifecycleScope.launchWhenCreated {
      viewModel.uiState.collect {
        if (it.isLoading) {
          binding.pgBar.root.visibility = View.VISIBLE
        } else if (!it.isLoading && it.error != null) {
          Toast.makeText(this@MainActivity, "unexpected error occurred", Toast.LENGTH_SHORT).show()
        }
        else {
          binding.pgBar.root.visibility = View.GONE
          val data = it.weatherInfo?.currentWeatherData
          Log.d("TAG", "setData: ${data?.temperatureCelsius}")
          binding.txtDate.text = data?.time?.format(DateTimeFormatter.ofPattern("hh:mm a"))
            ?: "hello"

          (data?.temperatureCelsius.toString() + "℃").also { binding.txtTemperature.text = it }

          data?.weatherType?.iconRes?.let { it1 -> binding.image.setImageResource(it1) }

          binding.description.text = data?.weatherType?.weatherDesc
          (data?.humidity.toString() + " %").also { binding.txtHumidity.text = it }
          (data?.windSpeed.toString() + " km/h").also { binding.txtWindSpeed.text = it }
          binding.mainContainer.background = data?.weatherType?.backgroundRes?.let { it1 ->
            resources?.getDrawable(
              it1
            )
          }

          locationTracker.getCurrentLocation()?.longitude?.let {
            locationTracker.getCurrentLocation()?.latitude?.let { it1 ->
              getAddress(it1,
                it)
            }
          }


          setupRecyclerView(
            it.weatherInfo?.weatherDataPerDay?.get(0),
            it.weatherInfo?.currentWeatherData?.weatherType
          )


        }


      }


    }


  }



  private fun getAddress(latitude: Double, longitude: Double) {
   // val result = StringBuilder()
    try {
      val geocoder = Geocoder(this, Locale.getDefault())
      val addresses = geocoder.getFromLocation(latitude, longitude, 1)
      if (addresses.size > 0) {
        val address = addresses[0]

        binding.txtPlace.text = address.locality
      //  result.append(address.locality).append("\n")
       // result.append(address.countryName)
      }
    } catch (e: IOException) {
      e.message?.let { Log.e("tag", it) }
    }

   // Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show()
   // Log.d(Companion.TAG, "getAddress: ${result.toString()}")

  }

  companion object {
    private const val TAG = "MainActivity"
  }
}