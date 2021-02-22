package com.aleksejantonov.core.db.impl.store

import com.aleksejantonov.core.db.api.DatabaseClientApi
import com.aleksejantonov.core.db.api.store.BeersStore
import com.aleksejantonov.core.db.entity.entity
import com.aleksejantonov.core.db.entity.model
import com.aleksejantonov.core.model.BeerModel
import com.aleksejantonov.core.model.FilterModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BeersStoreImpl @Inject constructor(private val db: DatabaseClientApi) : BeersStore {

  override fun insertBeers(beers: List<BeerModel>) {
    val ids = beers.map { it.id }.toSet()
    val existentBeers = db.beerDao().getBeers(ids).associateBy { it.id }
    db.beerDao().insertBeers(beers.map { it.entity().copy(isFavorite = existentBeers[it.id]?.isFavorite ?: false) })
  }

  override fun beerData(id: Long): Flow<BeerModel> {
    return db.beerDao().beerData(id).map { entity -> entity.model() }
  }

  override fun beersData(limit: Int, offset: Int, filterRequest: FilterModel): Flow<List<BeerModel>> {
    return db.beerDao().beersData(
      limit = limit,
      offset = offset,
      filterRequest.abvPair.first,
      filterRequest.abvPair.second,
      filterRequest.ibuPair.first,
      filterRequest.ibuPair.second,
      filterRequest.ebcPair.first,
      filterRequest.ebcPair.second
    ).map { it.map { entity -> entity.model() } }
  }

  override fun favoriteBeersData(limit: Int, offset: Int): Flow<List<BeerModel>> {
    return db.beerDao().favoriteBeersData(limit, offset).map { it.map { entity -> entity.model() } }
  }

  override fun favoriteBeersCountData(): Flow<Long> {
    return db.beerDao().favoriteBeersCountData()
  }

  override fun beersCount(): Int {
    return db.beerDao().beersCount().toInt()
  }

  override fun setFavorite(id: Long) {
    db.beerDao().setFavorite(id)
  }

  override fun toggleFavorite(id: Long) {
    db.beerDao().toggleFavorite(id)
  }
}