package com.aleksejantonov.feature.beerlist.impl.data

import com.aleksejantonov.core.di.RootScope
import com.aleksejantonov.core.ui.model.ListItem
import com.aleksejantonov.core.ui.base.adapter.delegate.PaginationLoadingItem
import com.aleksejantonov.core.ui.model.BeerItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@RootScope
class BeerListInteractorImpl @Inject constructor(
  private val repository: BeerListRepository
) : BeerListInteractor {

  override suspend fun data(): Flow<List<ListItem>> {
    return repository.data().map { dtoState ->
      mutableListOf<ListItem>().apply {
        addAll(dtoState.data.map { model -> BeerItem.from(model) })
        if (!dtoState.allLoadedEnd) add(PaginationLoadingItem)
      }
    }
  }

  override suspend fun loadMore() {
    repository.loadMore()
  }

  override suspend fun toggleFavorite(id: Long) {
    repository.toggleFavorite(id)
  }

}