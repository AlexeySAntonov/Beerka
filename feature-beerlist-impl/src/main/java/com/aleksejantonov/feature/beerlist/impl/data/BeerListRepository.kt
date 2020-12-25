package com.aleksejantonov.feature.beerlist.impl.data

import com.aleksejantonov.core.model.BeerModel
import com.aleksejantonov.core.ui.base.PagingState
import kotlinx.coroutines.flow.Flow

interface BeerListRepository {
  suspend fun data(): Flow<PagingState<BeerModel>>
  suspend fun loadMore()
}