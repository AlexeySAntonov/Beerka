package com.aleksejantonov.core.ui.base.bottomnavigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aleksejantonov.core.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BottomNavigationViewModel : BaseViewModel() {

  private val _showData = MutableLiveData(true)
  val showData: LiveData<Boolean> = _showData

  private var job: Job? = null

  var isVisible
    get() = showData.value ?: false
    set(value) {
      if (showData.value != value) {
        _showData.postValue(value)
      }
      job?.cancel()
      if (!value) {
        job = viewModelScope.launch(Dispatchers.Default) {
          delay(SHOW_DELAY)
          _showData.postValue(true)
        }
      }
    }

  private companion object {
    const val SHOW_DELAY = 2000L
  }
}