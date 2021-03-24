package com.aleksejantonov.feature.beerlist.impl.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aleksejantonov.core.di.ComponentKey
import com.aleksejantonov.core.ui.base.BaseViewModel
import com.aleksejantonov.core.di.ScreenData
import com.aleksejantonov.core.navigation.GlobalRouter
import com.aleksejantonov.core.ui.model.ListItem
import com.aleksejantonov.feature.beerlist.impl.data.BeerListInteractor
import com.aleksejantonov.core.ui.model.BeerItem
import com.aleksejantonov.feature.beerlist.impl.di.FeatureBeerListComponentsHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class BeerListViewModel @Inject constructor(
  @ComponentKey private val componentKey: String,
  private val interactor: BeerListInteractor,
  private val router: GlobalRouter
) : BaseViewModel() {

  private val _data = MutableLiveData<List<ListItem>>()
  val data: LiveData<List<ListItem>> = _data

  init {
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
      interactor.data().collect { _data.postValue(it) }
    }
  }

  fun loadMore() {
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
      interactor.loadMore()
    }
  }

  fun navigateToDetails(item: BeerItem) {
    router.openDetailsFeature(ScreenData(item.id))
  }

  fun toggleFavorite(id: Long) {
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
      interactor.toggleFavorite(id)
    }
  }

  override fun onCleared() {
    FeatureBeerListComponentsHolder.reset(componentKey)
  }
}