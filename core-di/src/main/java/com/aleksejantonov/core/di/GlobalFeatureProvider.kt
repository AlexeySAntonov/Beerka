package com.aleksejantonov.core.di

import androidx.fragment.app.Fragment
import com.aleksejantonov.feature.beerlist.api.di.FeatureBeerListApi
import com.aleksejantonov.feature.favorites.api.di.FeatureFavoritesApi
import javax.inject.Inject
import javax.inject.Provider

class GlobalFeatureProvider @Inject constructor(
    private val featureBeerListProvider: Provider<FeatureBeerListApi>,
    private val featureFavoritesApiProvider: Provider<FeatureFavoritesApi>
) {

    fun provideFeatureBeerList(): Fragment {
        return featureBeerListProvider.get().featureBeerListScreenProvider().screen()
    }

    fun provideFeatureFavorites(): Fragment {
        return featureFavoritesApiProvider.get().featureFavoritesScreenProvider().screen()
    }
}