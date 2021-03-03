package com.aleksejantonov.beerka.di

import com.aleksejantonov.core.api.BeersApi
import com.aleksejantonov.core.db.api.di.CoreDatabaseApi
import com.aleksejantonov.core.di.GlobalFeatureProvider
import com.aleksejantonov.core.mediator.api.FilterDataMediator
import com.aleksejantonov.core.navigation.GlobalRouter
import com.aleksejantonov.core.navigation.ModalsRouter
import com.aleksejantonov.feature.beerlist.api.di.FeatureBeerListApi
import com.aleksejantonov.feature.beerlist.impl.di.FeatureBeerListComponent
import com.aleksejantonov.feature.beerlist.impl.di.FeatureBeerListComponentDependencies
import com.aleksejantonov.feature.details.api.di.FeatureDetailsApi
import com.aleksejantonov.feature.details.impl.di.FeatureDetailsComponent
import com.aleksejantonov.feature.details.impl.di.FeatureDetailsComponentDependencies
import com.aleksejantonov.feature.favorites.api.di.FeatureFavoritesApi
import com.aleksejantonov.feature.favorites.impl.di.FeatureFavoritesComponent
import com.aleksejantonov.feature.favorites.impl.di.FeatureFavoritesComponentDependencies
import com.aleksejantonov.feature.filter.api.di.FeatureFilterApi
import com.aleksejantonov.feature.filter.impl.di.FeatureFilterComponent
import com.aleksejantonov.feature.filter.impl.di.FeatureFilterComponentDependencies
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Singleton

@Module
class FeatureProviderModule {

  @Provides
  @Singleton
  fun providesGlobalFeatureProvider(
    featureBeerListApiProvider: Provider<Pair<FeatureBeerListApi, String>>,
    featureFavoritesApiProvider: Provider<Pair<FeatureFavoritesApi, String>>,
    featureDetailsApiProvider: Provider<Pair<FeatureDetailsApi, String>>,
    featureFilterApiProvider: Provider<Pair<FeatureFilterApi, String>>
  ): GlobalFeatureProvider {
    return GlobalFeatureProvider(
      featureBeerListApiProvider,
      featureFavoritesApiProvider,
      featureDetailsApiProvider,
      featureFilterApiProvider
    )
  }

  @Provides
  @Singleton
  fun provideFeatureBeerListDependencies(
    beersApi: BeersApi,
    coreDatabaseApi: CoreDatabaseApi,
    filterDataMediator: FilterDataMediator,
    router: GlobalRouter
  ): FeatureBeerListComponentDependencies {
    return object : FeatureBeerListComponentDependencies {
      override fun beersApi(): BeersApi = beersApi
      override fun coreDatabaseApi(): CoreDatabaseApi = coreDatabaseApi
      override fun filterDataMediator(): FilterDataMediator = filterDataMediator
      override fun router(): GlobalRouter = router
    }
  }

  // Unscoped
  @Provides
  fun provideFeatureBeerListApi(
    dependencies: FeatureBeerListComponentDependencies
  ): Pair<FeatureBeerListApi, String> {
    return FeatureBeerListComponent.init(dependencies)
  }

  @Provides
  @Singleton
  fun provideFeatureFavoritesDependencies(
    coreDatabaseApi: CoreDatabaseApi,
    router: GlobalRouter
  ): FeatureFavoritesComponentDependencies {
    return object : FeatureFavoritesComponentDependencies {
      override fun coreDatabaseApi(): CoreDatabaseApi = coreDatabaseApi
      override fun router(): GlobalRouter = router
    }
  }

  // Unscoped
  @Provides
  fun provideFeatureFavoritesApi(
    dependencies: FeatureFavoritesComponentDependencies
  ): Pair<FeatureFavoritesApi, String> {
    return FeatureFavoritesComponent.init(dependencies)
  }

  @Provides
  @Singleton
  fun provideFeatureDetailsDependencies(
    coreDatabaseApi: CoreDatabaseApi,
    router: GlobalRouter
  ): FeatureDetailsComponentDependencies {
    return object : FeatureDetailsComponentDependencies {
      override fun coreDatabaseApi(): CoreDatabaseApi = coreDatabaseApi
      override fun router(): GlobalRouter = router
    }
  }

  // Unscoped
  @Provides
  fun provideFeatureDetailsApi(
    dependencies: FeatureDetailsComponentDependencies
  ): Pair<FeatureDetailsApi, String> {
    return FeatureDetailsComponent.init(dependencies)
  }

  @Provides
  @Singleton
  fun provideFeatureFilterDependencies(
    coreDatabaseApi: CoreDatabaseApi,
    filterDataMediator: FilterDataMediator,
    modalsRouter: ModalsRouter
  ): FeatureFilterComponentDependencies {
    return object : FeatureFilterComponentDependencies {
      override fun coreDatabaseApi(): CoreDatabaseApi = coreDatabaseApi
      override fun filterDataMediator(): FilterDataMediator = filterDataMediator
      override fun modalsRouter(): ModalsRouter = modalsRouter
    }
  }

  // Unscoped
  @Provides
  fun provideFeatureFilterApi(
    dependencies: FeatureFilterComponentDependencies
  ): Pair<FeatureFilterApi, String> {
    return FeatureFilterComponent.init(dependencies)
  }

}