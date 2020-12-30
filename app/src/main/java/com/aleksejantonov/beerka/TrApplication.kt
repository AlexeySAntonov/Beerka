package com.aleksejantonov.beerka

import android.app.Application
import com.aleksejantonov.beerka.di.DI
import com.facebook.stetho.Stetho
import timber.log.Timber

class TrApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DI.init(this)
        initStetho()
        initTimber()
    }

    private fun initStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}