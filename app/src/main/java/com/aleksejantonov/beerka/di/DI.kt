package com.aleksejantonov.beerka.di

import android.app.Application

object DI {
  lateinit var appComponent: AppComponent
    private set

  fun init(app: Application) {
    appComponent = DaggerAppComponent.builder()
      .appModule(AppModule(app))
      .build()
  }
}