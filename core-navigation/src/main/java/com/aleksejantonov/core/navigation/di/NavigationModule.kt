package com.aleksejantonov.core.navigation.di

import com.aleksejantonov.core.di.GlobalFeatureProvider
import com.aleksejantonov.core.navigation.GlobalRouter
import com.aleksejantonov.core.navigation.ModalsRouter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NavigationModule {

  @Provides
  @Singleton
  fun providesGlobalRouter(featureProvider: GlobalFeatureProvider, modalsRouter: ModalsRouter) = GlobalRouter(featureProvider, modalsRouter)

  @Provides
  @Singleton
  fun providesModalsRouter(featureProvider: GlobalFeatureProvider) = ModalsRouter(featureProvider)
}