package com.aleksejantonov.feature.filter.impl.di

import androidx.lifecycle.ViewModel
import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.core.ui.base.mvvm.ViewModelKey
import com.aleksejantonov.feature.filter.api.data.FeatureFilterViewProvider
import com.aleksejantonov.feature.filter.impl.data.*
import com.aleksejantonov.feature.filter.impl.ui.FilterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FeatureFilterModule {

  @Binds
  @FeatureScope
  abstract fun featureFilterScreenProvider(provider: FeatureFilterViewProviderImpl): FeatureFilterViewProvider

  @Binds
  @FeatureScope
  abstract fun featureFilterInteractor(interactor: FilterInteractorImpl): FilterInteractor

  @Binds
  @IntoMap
  @ViewModelKey(FilterViewModel::class)
  abstract fun bindFilterViewModel(viewModel: FilterViewModel): ViewModel
}