package com.aleksejantonov.feature.beerlist.impl.di

import androidx.lifecycle.ViewModel
import com.aleksejantonov.core.di.RootScope
import com.aleksejantonov.core.ui.base.mvvm.ViewModelKey
import com.aleksejantonov.feature.beerlist.api.data.FeatureBeerListScreenProvider
import com.aleksejantonov.feature.beerlist.impl.data.*
import com.aleksejantonov.feature.beerlist.impl.ui.BeerListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FeatureBeerListModule {

  @Binds
  @RootScope
  abstract fun featureBeerListScreenProvider(provider: FeatureBeerListScreenProviderImpl): FeatureBeerListScreenProvider

  @Binds
  @RootScope
  abstract fun featureBeerListScreenInteractor(interactor: FeatureBeerListScreenInteractorImpl): FeatureBeerListScreenInteractor

  @Binds
  @RootScope
  abstract fun featureBeerListScreenRepository(repository: FeatureBeerListScreenRepositoryImpl): FeatureBeerListScreenRepository

  @Binds
  @IntoMap
  @ViewModelKey(BeerListViewModel::class)
  abstract fun bindBeerListViewModel(viewModel: BeerListViewModel): ViewModel
}