package com.aleksejantonov.beerka.di

import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.di.GlobalFeatureProvider
import com.aleksejantonov.feature.beerlist.api.di.FeatureBeerListApi
import com.aleksejantonov.feature.beerlist.impl.di.FeatureBeerListComponent
import com.aleksejantonov.feature.beerlist.impl.di.FeatureBeerListComponentDependencies
import com.aleksejantonov.feature.favorites.api.di.FeatureFavoritesApi
import com.aleksejantonov.feature.favorites.impl.di.FeatureFavoritesComponent
import com.aleksejantonov.feature.favorites.impl.di.FeatureFavoritesComponentDependencies
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Singleton

@Module
class FeatureProviderModule {

  @Provides
  @Singleton
  fun providesGlobalFeatureProvider(
    featureBeerListProvider: Provider<FeatureBeerListApi>,
    featureFavoritesApiProvider: Provider<FeatureFavoritesApi>
  ): GlobalFeatureProvider {
    return GlobalFeatureProvider(featureBeerListProvider, featureFavoritesApiProvider)
  }

  @Provides
  @Singleton
  fun provideFeatureBeerListDependencies(
    coreDatabaseApi: CoreDatabaseApi
  ): FeatureBeerListComponentDependencies {
    return object : FeatureBeerListComponentDependencies {
      override fun coreDatabaseApi(): CoreDatabaseApi = coreDatabaseApi
    }
  }

  // Unscoped
  @Provides
  fun provideFeatureBeerListApi(
    dependencies: FeatureBeerListComponentDependencies
  ): FeatureBeerListApi {
    return FeatureBeerListComponent.init(dependencies)
  }

  @Provides
  @Singleton
  fun provideFeatureFavoritesDependencies(
    coreDatabaseApi: CoreDatabaseApi
  ): FeatureFavoritesComponentDependencies {
    return object : FeatureFavoritesComponentDependencies {
      override fun coreDatabaseApi(): CoreDatabaseApi = coreDatabaseApi
    }
  }

  // Unscoped
  @Provides
  fun provideFeatureFavoritesApi(
    dependencies: FeatureFavoritesComponentDependencies
  ): FeatureFavoritesApi {
    return FeatureFavoritesComponent.init(dependencies)
  }
}