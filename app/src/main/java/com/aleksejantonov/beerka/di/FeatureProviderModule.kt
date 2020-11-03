package com.aleksejantonov.beerka.di

import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.di.GlobalFeatureProvider
import com.aleksejantonov.feature.beerlist.api.di.FeatureBeerListApi
import com.aleksejantonov.feature.beerlist.impl.di.FeatureBeerListComponent
import com.aleksejantonov.feature.beerlist.impl.di.FeatureBeerListComponentDependencies
import com.aleksejantonov.feature.details.api.data.FeatureDetailsScreenProvider
import com.aleksejantonov.feature.details.api.di.FeatureDetailsApi
import com.aleksejantonov.feature.details.impl.di.FeatureDetailsComponent
import com.aleksejantonov.feature.details.impl.di.FeatureDetailsComponentDependencies
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
    featureFavoritesApiProvider: Provider<FeatureFavoritesApi>,
    featureDetailsApiProvider: Provider<FeatureDetailsApi>
  ): GlobalFeatureProvider {
    return GlobalFeatureProvider(
      featureBeerListProvider,
      featureFavoritesApiProvider,
      featureDetailsApiProvider
    )
  }

  @Provides
  @Singleton
  fun provideFeatureBeerListDependencies(
    coreDatabaseApi: CoreDatabaseApi,
    featureDetailsApi: FeatureDetailsApi
  ): FeatureBeerListComponentDependencies {
    return object : FeatureBeerListComponentDependencies {
      override fun coreDatabaseApi(): CoreDatabaseApi = coreDatabaseApi
      override fun featureDetailsScreenProvider(): FeatureDetailsScreenProvider = featureDetailsApi.featureDetailsScreenProvider()
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

  @Provides
  @Singleton
  fun provideFeatureDetailsDependencies(
    coreDatabaseApi: CoreDatabaseApi
  ): FeatureDetailsComponentDependencies {
    return object : FeatureDetailsComponentDependencies {
      override fun coreDatabaseApi(): CoreDatabaseApi = coreDatabaseApi
    }
  }

  // Unscoped
  @Provides
  fun provideFeatureDetailsApi(
    dependencies: FeatureDetailsComponentDependencies
  ): FeatureDetailsApi {
    return FeatureDetailsComponent.init(dependencies)
  }
}