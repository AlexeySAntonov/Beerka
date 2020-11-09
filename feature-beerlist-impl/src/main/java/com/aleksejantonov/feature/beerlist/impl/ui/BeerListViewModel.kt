package com.aleksejantonov.feature.beerlist.impl.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aleksejantonov.core.navigation.AppRouter
import com.aleksejantonov.core.ui.base.BaseViewModel
import com.aleksejantonov.core.di.ScreenData
import com.aleksejantonov.core.ui.base.adapter.ListItem
import com.aleksejantonov.feature.beerlist.impl.data.FeatureBeerListScreenInteractor
import com.aleksejantonov.feature.beerlist.impl.ui.delegate.item.BeerItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class BeerListViewModel @Inject constructor(
  private val interactor: FeatureBeerListScreenInteractor
) : BaseViewModel() {

  private val _data = MutableLiveData<List<ListItem>>()
  val data: LiveData<List<ListItem>> = _data

  init {
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
      interactor.data().collect { _data.postValue(it) }
    }
  }

  fun navigateToDetails(item: BeerItem) {
    AppRouter.openDetailsFeature(ScreenData(item.id, item.name, item.description, item.image))
  }
}