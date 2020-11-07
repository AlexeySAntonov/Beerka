package com.aleksejantonov.beerka.di

import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.di.GlobalFeatureProvider
import com.aleksejantonov.feature.beerlist.api.di.FeatureBeerListApi
import com.aleksejantonov.feature.beerlist.impl.di.FeatureBeerListComponent
import com.aleksejantonov.feature.beerlist.impl.di.FeatureBeerListComponentDependencies
import com.aleksejantonov.feature.details.api.di.FeatureDetailsApi
import com.aleksejantonov.feature.details.impl.di.FeatureDetailsComponent
import com.aleksejantonov.feature.details.impl.di.FeatureDetailsComponentDependencies
import com.aleksejantonov.feature.details.next.api.di.FeatureDetailsNextApi
import com.aleksejantonov.feature.details.next.impl.di.FeatureDetailsNextComponent
import com.aleksejantonov.feature.details.next.impl.di.FeatureDetailsNextComponentDependencies
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
    featureBeerListApiProvider: Provider<FeatureBeerListApi>,
    featureFavoritesApiProvider: Provider<FeatureFavoritesApi>,
    featureDetailsApiProvider: Provider<FeatureDetailsApi>,
    featureDetailsNextApiProvider: Provider<FeatureDetailsNextApi>
  ): GlobalFeatureProvider {
    return GlobalFeatureProvider(
      featureBeerListApiProvider,
      featureFavoritesApiProvider,
      featureDetailsApiProvider,
      featureDetailsNextApiProvider
    )
  }

  @Provides
  @Singleton
  fun provideFeatureBeerListDependencies(
    coreDatabaseApi: CoreDatabaseApi,
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

  @Provides
  @Singleton
  fun provideFeatureDetailsNextDependencies(
    coreDatabaseApi: CoreDatabaseApi
  ): FeatureDetailsNextComponentDependencies {
    return object : FeatureDetailsNextComponentDependencies {
      override fun coreDatabaseApi(): CoreDatabaseApi = coreDatabaseApi
    }
  }

  // Unscoped
  @Provides
  fun provideFeatureDetailsNextApi(
    dependencies: FeatureDetailsNextComponentDependencies
  ): FeatureDetailsNextApi {
    return FeatureDetailsNextComponent.init(dependencies)
  }
}