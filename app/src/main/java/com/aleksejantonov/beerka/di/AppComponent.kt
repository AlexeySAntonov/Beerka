package com.aleksejantonov.beerka.di

import com.aleksejantonov.core.di.GlobalFeatureProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    AppModule::class,
    FeatureProviderModule::class
  ]
)
interface AppComponent {
  fun globalFeatureProvider(): GlobalFeatureProvider
}