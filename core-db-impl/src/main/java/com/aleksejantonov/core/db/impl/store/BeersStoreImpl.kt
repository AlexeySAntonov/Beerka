package com.aleksejantonov.core.db.impl.store

import com.aleksejantonov.core.db.api.DatabaseClientApi
import com.aleksejantonov.core.db.api.store.BeersStore
import com.aleksejantonov.core.db.entity.entity
import com.aleksejantonov.core.db.entity.model
import com.aleksejantonov.core.model.BeerModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BeersStoreImpl @Inject constructor(private val db: DatabaseClientApi) : BeersStore {

  override fun insertBeers(beers: List<BeerModel>) {
    db.beerDao().insertBeers(beers.map { it.entity() })
  }

  override fun beerData(id: Long): Flow<BeerModel> {
    return db.beerDao().beerData(id).map { entity -> entity.model() }
  }

  override fun beersData(limit: Int, offset: Int): Flow<List<BeerModel>> {
    return db.beerDao().beersData(limit, offset).map { it.map { entity -> entity.model() } }
  }

  override fun favoriteBeersData(limit: Int, offset: Int): Flow<List<BeerModel>> {
    return db.beerDao().favoriteBeersData(limit, offset).map { it.map { entity -> entity.model() } }
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