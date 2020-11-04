package com.aleksejantonov.core.di

import androidx.fragment.app.Fragment
import com.aleksejantonov.feature.beerlist.api.di.FeatureBeerListApi
import com.aleksejantonov.feature.details.api.di.FeatureDetailsApi
import com.aleksejantonov.feature.favorites.api.di.FeatureFavoritesApi
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class GlobalFeatureProvider @Inject constructor(
    private val featureBeerListApiProvider: Provider<FeatureBeerListApi>,
    private val featureFavoritesApiProvider: Provider<FeatureFavoritesApi>,
    private val featureDetailsApiProvider: Provider<FeatureDetailsApi>
) {

    fun provideFeatureBeerList(): Fragment {
        val component = featureBeerListApiProvider.get()
        val componentKey = System.currentTimeMillis()
        ComponentsManager.save(componentKey, component)
        return component.featureBeerListScreenProvider().screen(componentKey)
    }

    fun provideFeatureFavorites(): Fragment {
        val component = featureFavoritesApiProvider.get()
        val componentKey = System.currentTimeMillis()
        ComponentsManager.save(componentKey, component)
        return component.featureFavoritesScreenProvider().screen(componentKey)
    }

    fun provideFeatureDetails(screenData: ScreenData): Fragment {
        val component = featureDetailsApiProvider.get()
        val componentKey = System.currentTimeMillis()
        ComponentsManager.save(componentKey, component)
        return component.featureDetailsScreenProvider().screen(componentKey, screenData)
    }
}