package com.aleksejantonov.feature.beerlist.impl.data

import com.aleksejantonov.core.model.BeerModel
import kotlinx.coroutines.flow.Flow

interface FeatureBeerListScreenRepository {
  suspend fun data(): Flow<List<BeerModel>>
}