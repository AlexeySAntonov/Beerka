package com.aleksejantonov.beerka.di

import android.app.Application
import com.aleksejantonov.core.navigation.AppRouter

object DI {
  lateinit var appComponent: AppComponent
    private set

  fun init(app: Application) {
    appComponent = DaggerAppComponent.builder()
      .appModule(AppModule(app))
      .build()

    AppRouter.attachFeatureProvider(appComponent.globalFeatureProvider())
  }
}