package com.aleksejantonov.core.db.api.dao

import androidx.room.*
import com.aleksejantonov.core.db.entity.BeerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BeerDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertBeer(beer: BeerEntity)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertBeers(beers: List<BeerEntity>)

  @Query("SELECT * FROM beers LIMIT :limit OFFSET :offset")
  fun beersData(limit: Int, offset: Int): Flow<List<BeerEntity>>

  @Query("SELECT COUNT(*) FROM beers")
  fun beersCount(): Long
}