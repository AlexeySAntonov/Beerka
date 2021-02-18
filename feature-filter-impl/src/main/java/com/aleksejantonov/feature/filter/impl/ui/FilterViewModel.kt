package com.aleksejantonov.feature.filter.impl.ui

import androidx.lifecycle.viewModelScope
import com.aleksejantonov.core.ui.base.BaseViewModel
import com.aleksejantonov.core.ui.model.ListItem
import com.aleksejantonov.feature.filter.impl.data.FilterInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilterViewModel @Inject constructor(
  private val interactor: FilterInteractor
) : BaseViewModel() {

  private val _data = MutableSharedFlow<List<ListItem>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
  val data: SharedFlow<List<ListItem>> = _data

  init {
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
      interactor.data().collect {
        _data.emit(it)
      }
    }
  }

}