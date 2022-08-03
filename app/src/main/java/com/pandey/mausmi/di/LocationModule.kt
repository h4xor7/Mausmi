package com.pandey.mausmi.di

import com.pandey.mausmi.data.location.DefaultLocationTracker
import com.pandey.mausmi.domain.location.LocationTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

  @Binds
  @Singleton
  abstract fun bindLocationTracker(defaultLocationTracker: DefaultLocationTracker): LocationTracker
}