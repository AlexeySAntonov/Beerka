package com.aleksejantonov.feature.beerlist.impl.data

import com.aleksejantonov.core.di.RootScope
import com.aleksejantonov.core.ui.base.adapter.ListItem
import com.aleksejantonov.feature.beerlist.impl.ui.delegate.item.BeerItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@RootScope
class FeatureBeerListScreenInteractorImpl @Inject constructor(
  private val repository: FeatureBeerListScreenRepository
) : FeatureBeerListScreenInteractor {

  override suspend fun data(): Flow<List<ListItem>> {
    return repository.data().map { it.map { model -> BeerItem.from(model) } }
  }
}