package com.aleksejantonov.beerka.di

import com.aleksejantonov.core.api.di.NetworkModule
import com.aleksejantonov.core.di.GlobalFeatureProvider
import com.aleksejantonov.core.navigation.GlobalRouter
import com.aleksejantonov.core.navigation.di.NavigationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    AppModule::class,
    FeatureProviderModule::class,
    NetworkModule::class,
    NavigationModule::class,
    MediatorModule::class,
  ]
)
interface AppComponent {
  fun globalRouter(): GlobalRouter
  fun globalFeatureProvider(): GlobalFeatureProvider
}