package com.pandey.mausmi.di

import com.pandey.mausmi.data.location.DefaultLocationTracker
import com.pandey.mausmi.data.repository.MausmiRepositoryImpl
import com.pandey.mausmi.domain.location.LocationTracker
import com.pandey.mausmi.domain.repository.MausmiRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

  @Binds
  @Singleton
  abstract fun bindMausmiRepository (mausmiRepositoryImpl: MausmiRepositoryImpl): MausmiRepository
}