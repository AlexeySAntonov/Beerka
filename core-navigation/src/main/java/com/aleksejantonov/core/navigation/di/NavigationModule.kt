package com.aleksejantonov.core.navigation.di

import com.aleksejantonov.core.di.GlobalFeatureProvider
import com.aleksejantonov.core.navigation.GlobalRouter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NavigationModule {

  @Provides
  @Singleton
  fun providesGlobalRouter(featureProvider: GlobalFeatureProvider) = GlobalRouter(featureProvider)
}