package com.aleksejantonov.core.db.api.store

import com.aleksejantonov.core.model.BeerModel
import com.aleksejantonov.core.model.FilterModel
import kotlinx.coroutines.flow.Flow

interface BeersStore {
  fun insertBeers(beers: List<BeerModel>)
  fun beerData(id: Long): Flow<BeerModel>
  fun beersData(limit: Int, offset: Int, filterRequest: FilterModel): Flow<List<BeerModel>>
  fun favoriteBeersData(limit: Int, offset: Int): Flow<List<BeerModel>>
  fun favoriteBeersCountData(): Flow<Long>
  fun beersCount(): Int
  fun setFavorite(id: Long)
  fun toggleFavorite(id: Long)
}