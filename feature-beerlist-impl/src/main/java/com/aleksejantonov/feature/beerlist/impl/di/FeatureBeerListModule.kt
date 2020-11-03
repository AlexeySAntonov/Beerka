package com.aleksejantonov.feature.beerlist.impl.di

import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.feature.beerlist.api.data.FeatureBeerListScreenProvider
import com.aleksejantonov.feature.beerlist.impl.data.FeatureBeerListScreenProviderImpl
import dagger.Binds
import dagger.Module

@Module
abstract class FeatureBeerListModule {

  @Binds
  @FeatureScope
  abstract fun featureBeerListScreenProvider(provider: FeatureBeerListScreenProviderImpl): FeatureBeerListScreenProvider
}