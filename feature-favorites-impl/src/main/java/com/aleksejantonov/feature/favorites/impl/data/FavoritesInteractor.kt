package com.aleksejantonov.feature.favorites.impl.data

import com.aleksejantonov.core.ui.model.ListItem
import kotlinx.coroutines.flow.Flow

interface FavoritesInteractor {
  suspend fun data(): Flow<List<ListItem>>
  suspend fun loadMore()
}