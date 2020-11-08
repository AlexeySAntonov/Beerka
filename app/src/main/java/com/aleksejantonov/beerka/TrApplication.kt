package com.aleksejantonov.beerka

import android.app.Application
import com.aleksejantonov.beerka.di.DI
import com.facebook.stetho.Stetho

class TrApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DI.init(this)
        initStetho()
    }

    private fun initStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }
}