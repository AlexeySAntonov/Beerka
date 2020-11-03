package com.aleksejantonov.beerka.di

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

}