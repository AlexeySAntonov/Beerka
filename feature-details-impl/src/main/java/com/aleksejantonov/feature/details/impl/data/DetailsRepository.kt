package com.aleksejantonov.feature.details.impl.data

import com.aleksejantonov.core.model.BeerModel
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {
  fun data(beerId: Long): Flow<BeerModel>
  fun toggleFavorite(beerId: Long)
}