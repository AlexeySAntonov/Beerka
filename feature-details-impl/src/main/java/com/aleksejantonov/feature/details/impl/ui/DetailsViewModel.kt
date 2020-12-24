package com.aleksejantonov.feature.details.impl.ui

import androidx.lifecycle.viewModelScope
import com.aleksejantonov.core.ui.base.BaseViewModel
import com.aleksejantonov.core.ui.model.BeerItem
import com.aleksejantonov.feature.details.impl.data.DetailsInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
  private val interactor: DetailsInteractor
) : BaseViewModel() {

  private val _data = MutableSharedFlow<BeerItem>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
  val data: SharedFlow<BeerItem> = _data

  init {
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
}