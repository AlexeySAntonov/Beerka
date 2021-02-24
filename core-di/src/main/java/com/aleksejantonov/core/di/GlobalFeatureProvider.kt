package com.aleksejantonov.core.di

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.aleksejantonov.feature.beerlist.api.di.FeatureBeerListApi
import com.aleksejantonov.feature.details.api.di.FeatureDetailsApi
import com.aleksejantonov.feature.favorites.api.di.FeatureFavoritesApi
import com.aleksejantonov.feature.filter.api.di.FeatureFilterApi
import java.util.*
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

  fun provideFeatureBeerList(): String {
    val (component, componentKey) = featureBeerListApiProvider.get()
    return UUID.randomUUID().toString().also { screenKey ->
      save(screenKey, component.featureBeerListScreenProvider().screen(componentKey))
    }
  }

  fun provideFeatureFavorites(): String {
    val (component, componentKey) = featureFavoritesApiProvider.get()
    return UUID.randomUUID().toString().also { screenKey ->
      save(screenKey, component.featureFavoritesScreenProvider().screen(componentKey))
    }
  }

  fun provideFeatureDetails(screenData: ScreenData): String {
    val (component, componentKey) = featureDetailsApiProvider.get()
    return UUID.randomUUID().toString().also { screenKey ->
      save(screenKey, component.featureDetailsScreenProvider().screen(componentKey, screenData))
    }
  }

  fun provideFeatureFilter(context: Context): View {
    val (component, componentKey) = featureFilterApiProvider.get()
    return component.featureFilterViewProvider().view(componentKey, context)
  }

  companion object {
    private val fragmentsMap = mutableMapOf<String, Fragment>()

    @Synchronized
    fun save(screenKey: String, fragment: Fragment) {
      fragmentsMap[screenKey] = fragment
    }

    @Synchronized
    fun pop(screenKey: String): Fragment? {
      return fragmentsMap.remove(screenKey)
    }

  }
}