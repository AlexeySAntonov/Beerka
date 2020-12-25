package com.aleksejantonov.feature.beerlist.impl.data

import com.aleksejantonov.core.ui.model.ListItem
import kotlinx.coroutines.flow.Flow

interface BeerListInteractor {
  suspend fun data(): Flow<List<ListItem>>
  suspend fun loadMore()
}