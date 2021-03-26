package com.aleksejantonov.feature.details.impl.ui

import androidx.lifecycle.viewModelScope
import com.aleksejantonov.core.di.ComponentKey
import com.aleksejantonov.core.navigation.GlobalRouter
import com.aleksejantonov.core.ui.base.BaseViewModel
import com.aleksejantonov.core.ui.model.BeerItem
import com.aleksejantonov.feature.details.impl.data.DetailsInteractor
import com.aleksejantonov.feature.details.impl.di.FeatureDetailsComponentsHolder
import com.aleksejantonov.module.injector.ScreenCustomDependencies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
  @ComponentKey private val componentKey: String,
  private val interactor: DetailsInteractor,
  private val router: GlobalRouter
) : BaseViewModel() {

  private val _data = MutableSharedFlow<BeerItem>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
  val data: SharedFlow<BeerItem> = _data

  init {
    Timber.e("ViewModel init component key: $componentKey")
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
      interactor.data().collect {
        _data.emit(it)
      }
    }
  }

  fun toggleFavorite() {
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
      interactor.toggleFavorite()
    }
  }

  fun testForCycleNavigation() {
    router.openDetailsFeature(ScreenCustomDependencies(4L))
  }

  fun onBack() {
    router.back()
  }

  override fun onCleared() {
    FeatureDetailsComponentsHolder.reset(componentKey)
  }
}