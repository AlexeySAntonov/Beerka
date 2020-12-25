package com.aleksejantonov.feature.favorites.impl.di

import androidx.lifecycle.ViewModel
import com.aleksejantonov.core.di.RootScope
import com.aleksejantonov.core.ui.base.mvvm.ViewModelKey
import com.aleksejantonov.feature.favorites.api.data.FeatureFavoritesScreenProvider
import com.aleksejantonov.feature.favorites.impl.data.*
import com.aleksejantonov.feature.favorites.impl.ui.FavoritesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FeatureFavoritesModule {

  @Binds
  @RootScope
  abstract fun featureFavoritesScreenProvider(provider: FeatureFavoritesScreenProviderImpl): FeatureFavoritesScreenProvider

  @Binds
  @RootScope
  abstract fun favoritesInteractor(interactor: FavoritesInteractorImpl): FavoritesInteractor

  @Binds
  @RootScope
  abstract fun favoritesRepository(Repository: FavoritesRepositoryImpl): FavoritesRepository

  @Binds
  @IntoMap
  @ViewModelKey(FavoritesViewModel::class)
  abstract fun provideFavoritesViewModel(viewModel: FavoritesViewModel): ViewModel
}