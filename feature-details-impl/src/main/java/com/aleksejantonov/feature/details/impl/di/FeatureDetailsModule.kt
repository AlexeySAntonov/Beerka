package com.aleksejantonov.feature.details.impl.di

import androidx.lifecycle.ViewModel
import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.core.ui.base.mvvm.ViewModelKey
import com.aleksejantonov.feature.details.api.data.FeatureDetailsScreenProvider
import com.aleksejantonov.feature.details.impl.data.FeatureDetailsScreenProviderImpl
import com.aleksejantonov.feature.details.impl.ui.DetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FeatureDetailsModule {

  @Binds
  @FeatureScope
  abstract fun featureDetailsScreenProvider(provider: FeatureDetailsScreenProviderImpl): FeatureDetailsScreenProvider

  @Binds
  @IntoMap
  @ViewModelKey(DetailsViewModel::class)
  abstract fun bindDetailsViewModel(viewModel: DetailsViewModel): ViewModel
}