package com.aleksejantonov.core.db.api.store

import com.aleksejantonov.core.model.BeerModel
import kotlinx.coroutines.flow.Flow

interface BeersStore {
  fun insertBeers(beers: List<BeerModel>)
  fun beersData(limit: Int, offset: Int): Flow<List<BeerModel>>
  fun beersCount(): Int
}