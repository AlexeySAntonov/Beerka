package com.aleksejantonov.core.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

  val firstStartEvent: LiveData<Unit> = SingleLiveEvent<Unit>().also { it.postValue(Unit) }

  open val exceptionHandler = CoroutineExceptionHandler { _, e -> Timber.e(e) }

}