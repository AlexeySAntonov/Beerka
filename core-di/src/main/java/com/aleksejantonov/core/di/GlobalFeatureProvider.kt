package com.aleksejantonov.core.di

import androidx.fragment.app.Fragment
import com.aleksejantonov.feature.favorites.api.di.FeatureFavoritesApi
import javax.inject.Inject
import javax.inject.Provider

class GlobalFeatureProvider @Inject constructor(
    private val featureFavoritesApiProvider: Provider<FeatureFavoritesApi>
) {

    fun provideFeatureFavorites(): Fragment {
        return featureFavoritesApiProvider.get().featureFavoritesScreenProvider().screen()
    }
}