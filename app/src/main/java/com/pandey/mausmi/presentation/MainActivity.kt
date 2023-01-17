package com.pandey.mausmi.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.pandey.mausmi.databinding.ActivityMainBinding
import com.pandey.mausmi.domain.location.LocationTracker
import com.pandey.mausmi.domain.weather.WeatherData
import com.pandey.mausmi.domain.weather.WeatherType
import dagger.hilt.android.AndroidEntryPoint
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
  private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
  @RequiresApi(Build.VERSION_CODES.O)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)

    val view = binding.root
    setContentView(view)

    locationHandler()
    setData()
  }

  private fun locationHandler() {
    val locationManager =
      application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

    if (!isGpsEnabled) {
      showGpsPopUp()

    }

    permissionLauncher =
      registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {

        getLastLocation()
      }
    permissionLauncher.launch(
      arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
      )
    )
    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)


  }

  private fun showGpsPopUp() {
    AlertDialog.Builder(this)
      .setMessage("gps network not enabled")
      .setPositiveButton(
        "open location settings"
      ) { _, _ ->
        this.startActivity(
          Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        )
      }
      .setNegativeButton("Cancel", null)
      .show()
  }


  @SuppressLint("MissingPermission")
  private fun getLastLocation() {
    fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
      val location: Location? = task.result
      if (location == null) {
        newLocationData()
      } else {


        getCityName(location.latitude, location.longitude)

        Log.d("Debug:", "Your Location:" + location.longitude)
      }
    }
  }

  private fun getCityName(lat: Double, long: Double) {

    val area: String

    val geoCoder = Geocoder(this, Locale.getDefault())
    val address = geoCoder.getFromLocation(lat, long, 3)

    area = address?.get(0)?.subLocality.toString()

    binding.txtPlace.text = area

    bindHandler(lat, long)


  }

  private fun bindHandler(lat: Double, long: Double) {
    viewModel.loadWeatherData(lat,long)
  }

  @Suppress("DEPRECATION")
  @SuppressLint("MissingPermission")
  private fun newLocationData() {
    val locationRequest = LocationRequest()
    locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    locationRequest.interval = 0
    locationRequest.fastestInterval = 0
    locationRequest.numUpdates = 1
    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    fusedLocationProviderClient.requestLocationUpdates(
      locationRequest, locationCallback, Looper.myLooper()
    )
  }


  private val locationCallback = object : LocationCallback() {
    override fun onLocationResult(locationResult: LocationResult) {
      val lastLocation: Location? = locationResult.lastLocation
      Log.d(
        TAG, "your last last location: " + lastLocation?.longitude.toString() + "," +
                "" + lastLocation?.longitude.toString()
      )


      lastLocation?.latitude?.let {
        getCityName(
          it,
          lastLocation.longitude
        )
      }


    }
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



          setupRecyclerView(
            it.weatherInfo?.weatherDataPerDay?.get(0),
            it.weatherInfo?.currentWeatherData?.weatherType
          )


        }


      }


    }


  }




  companion object {
    private const val TAG = "MainActivity"
  }
}