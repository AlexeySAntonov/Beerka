package com.aleksejantonov.beerka.di

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FeatureProviderModule {
    @Provides
    @Singleton
    fun provideFeatureFavoritesDependencies(): {

    }
}