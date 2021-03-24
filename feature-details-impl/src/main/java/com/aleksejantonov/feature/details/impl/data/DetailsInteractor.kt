package com.aleksejantonov.feature.details.impl.data

import com.aleksejantonov.core.ui.model.BeerItem
import kotlinx.coroutines.flow.Flow

interface DetailsInteractor {
  suspend fun data(beerId: Long): Flow<BeerItem>
  fun toggleFavorite(beerId: Long)
}