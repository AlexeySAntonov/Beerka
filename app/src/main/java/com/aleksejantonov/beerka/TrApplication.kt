package com.aleksejantonov.beerka

import android.app.Application
import com.aleksejantonov.beerka.di.DI

class TrApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DI.init(this)
    }
}