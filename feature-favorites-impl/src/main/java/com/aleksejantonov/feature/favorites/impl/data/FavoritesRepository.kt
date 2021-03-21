package com.aleksejantonov.feature.favorites.impl.data

import com.aleksejantonov.core.model.BeerModel
import com.aleksejantonov.core.ui.base.PagingState
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
  suspend fun data(): Flow<PagingState<BeerModel>>
  suspend fun loadMore()
  suspend fun removeFromFavorites(id: Long)
}