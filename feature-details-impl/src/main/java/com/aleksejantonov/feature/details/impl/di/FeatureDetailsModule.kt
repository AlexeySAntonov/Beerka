package com.aleksejantonov.feature.details.impl.di

import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.feature.details.api.data.FeatureDetailsScreenProvider
import com.aleksejantonov.feature.details.impl.data.FeatureDetailsScreenProviderImpl
import dagger.Binds
import dagger.Module

@Module
abstract class FeatureDetailsModule {

  @Binds
  @FeatureScope
  abstract fun featureDetailsScreenProvider(provider: FeatureDetailsScreenProviderImpl): FeatureDetailsScreenProvider
}