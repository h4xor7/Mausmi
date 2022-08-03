package com.pandey.mausmi.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.pandey.mausmi.databinding.ItemForecastBinding
import com.pandey.mausmi.domain.weather.WeatherData
import com.pandey.mausmi.domain.weather.WeatherInfo
import com.pandey.mausmi.domain.weather.WeatherType
import java.time.format.DateTimeFormatter

class MainAdapter(val context: Context, val currentWeatherType: WeatherType?) :
  RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
  private var listData = emptyList<WeatherData>()


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
    val itemForecastBinding =
      ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return MainViewHolder(itemForecastBinding)
  }

  @SuppressLint("UseCompatLoadingForDrawables")
  @RequiresApi(Build.VERSION_CODES.O)
  override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
    holder.itemForecastBinding.txtTime.text =
      listData[position].time.format(DateTimeFormatter.ofPattern("hh:mm a")).toString()
    (listData[position].temperatureCelsius.toString() + "℃").also {
      holder.itemForecastBinding.txtTemp.text = it
    }
    holder.itemForecastBinding.icon.setImageResource(listData[position].weatherType.iconRes)
    holder.itemForecastBinding.itemContainer.background = currentWeatherType?.cardbackRes?.let {
      context.getDrawable(
        it
      )
    }
  }

  override fun getItemCount(): Int {
    return listData.size
  }

  internal fun setWeatherData(data: List<WeatherData>) {
    this.listData = data
    notifyDataSetChanged()

  }


  inner class MainViewHolder(val itemForecastBinding: ItemForecastBinding) :
    RecyclerView.ViewHolder(itemForecastBinding.root)

}