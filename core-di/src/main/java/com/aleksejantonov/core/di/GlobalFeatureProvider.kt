package com.aleksejantonov.core.di

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.aleksejantonov.feature.beerlist.api.di.FeatureBeerListApi
import com.aleksejantonov.feature.details.api.di.FeatureDetailsApi
import com.aleksejantonov.feature.favorites.api.di.FeatureFavoritesApi
import com.aleksejantonov.feature.filter.api.di.FeatureFilterApi
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class GlobalFeatureProvider @Inject constructor(
    private val featureBeerListApiProvider: Provider<Pair<FeatureBeerListApi, String>>,
    private val featureFavoritesApiProvider: Provider<Pair<FeatureFavoritesApi, String>>,
    private val featureDetailsApiProvider: Provider<Pair<FeatureDetailsApi, String>>,
    private val featureFilterApiProvider: Provider<Pair<FeatureFilterApi, String>>
) {

    fun provideFeatureBeerList(): Fragment {
        val (component, componentKey) = featureBeerListApiProvider.get()
        return component.featureBeerListScreenProvider().screen(componentKey)
    }

    fun provideFeatureFavorites(): Fragment {
        val (component, componentKey) = featureFavoritesApiProvider.get()
        return component.featureFavoritesScreenProvider().screen(componentKey)
    }

    fun provideFeatureDetails(screenData: ScreenData): Fragment {
        val (component, componentKey) = featureDetailsApiProvider.get()
        return component.featureDetailsScreenProvider().screen(componentKey, screenData)
    }

    fun provideFeatureFilter(context: Context): View {
        val (component, componentKey) = featureFilterApiProvider.get()
        return component.featureFilterViewProvider().view(componentKey, context)
    }

}