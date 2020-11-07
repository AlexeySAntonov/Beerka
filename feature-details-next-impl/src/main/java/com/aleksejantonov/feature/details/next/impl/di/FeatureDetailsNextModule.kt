package com.aleksejantonov.feature.details.next.impl.di

import androidx.lifecycle.ViewModel
import com.aleksejantonov.core.di.FeatureScope
import com.aleksejantonov.core.ui.base.mvvm.ViewModelKey
import com.aleksejantonov.feature.details.next.api.data.FeatureDetailsNextScreenProvider
import com.aleksejantonov.feature.details.next.impl.data.FeatureDetailsNextScreenProviderImpl
import com.aleksejantonov.feature.details.next.impl.ui.DetailsNextViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FeatureDetailsNextModule {

  @Binds
  @FeatureScope
  abstract fun featureDetailsNextScreenProvider(provider: FeatureDetailsNextScreenProviderImpl): FeatureDetailsNextScreenProvider

  @Binds
  @IntoMap
  @ViewModelKey(DetailsNextViewModel::class)
  abstract fun bindDetailsNextViewModel(viewModel: DetailsNextViewModel): ViewModel
}