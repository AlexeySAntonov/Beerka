package com.aleksejantonov.feature.favorites.impl.di

import com.aleksejantonov.core.di.RootScope
import com.aleksejantonov.feature.favorites.api.data.FeatureFavoritesScreenProvider
import com.aleksejantonov.feature.favorites.impl.data.FeatureFavoritesScreenProviderImpl
import dagger.Binds
import dagger.Module

@Module
abstract class FeatureFavoritesModule {

  @Binds
  @RootScope
  abstract fun featureFavoritesScreenProvider(provider: FeatureFavoritesScreenProviderImpl): FeatureFavoritesScreenProvider
}